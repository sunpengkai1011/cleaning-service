package hottopic.mit.co.nz.cleaningservice.model.payment;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.cleaning.CleaningOrder;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class PaymentModel implements IPayment {
    private Context context;

    public PaymentModel(Context context) {
        this.context = context;
    }

    @Override
    public boolean paymentByCard(float amount, String cardNo, int orderId, String feedback) {
        if (!TextUtils.isEmpty(cardNo) && amount != 0){
            List<CleaningOrder> orders = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, Constants.SP_KEY_ORDERS), new TypeToken<List<Order>>(){}.getType());
            if (!TextUtils.isEmpty(feedback)){
                orders.get(orderId).setFeedback(feedback);
            }
            orders.get(orderId).setStatus(Constants.STATUS_ORDER_PAID);
            GeneralUtil.storDataBySP(context, Constants.SP_KEY_ORDERS, GeneralUtil.toJson(orders, new TypeToken<List<Order>>(){}.getType()));
            return true;
        }
        return false;
    }

    @Override
    public boolean paymentByBalance(float amount, UserInfo userInfo, int orderId, String feedback) {
        if (userInfo != null && amount != 0){
            List<CleaningOrder> orders = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, Constants.SP_KEY_ORDERS), new TypeToken<List<Order>>(){}.getType());
            if (!TextUtils.isEmpty(feedback)){
                orders.get(orderId).setFeedback(feedback);
            }
            orders.get(orderId).setStatus(Constants.STATUS_ORDER_PAID);
            float balance = userInfo.getBalance() - amount;
            userInfo.setBalance(balance);
            GeneralUtil.storDataBySP(context, Constants.SP_KEY_ORDERS, GeneralUtil.toJson(orders, new TypeToken<List<Order>>(){}.getType()));
            GeneralUtil.storDataBySP(context, userInfo.getUserName(), GeneralUtil.toJson(userInfo, UserInfo.class));
            return true;
        }
        return false;
    }
}
