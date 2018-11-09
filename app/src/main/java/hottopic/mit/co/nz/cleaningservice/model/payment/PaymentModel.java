package hottopic.mit.co.nz.cleaningservice.model.payment;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class PaymentModel implements IPayment {
    private Context context;

    public PaymentModel(Context context) {
        this.context = context;
    }

    @Override
    public boolean paymentByCard(float amount, String cardNo, String userId, int orderId, String feedback, int rating) {
        if (!TextUtils.isEmpty(cardNo)){
            List<Order> orders = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, userId), new TypeToken<List<Order>>(){}.getType());
            if (!TextUtils.isEmpty(feedback)){
                orders.get(orderId).setFeedback(feedback);
            }
            orders.get(orderId).setStatus(Constants.STATUS_ORDER_PAID);
            orders.get(orderId).setRating(rating);
            GeneralUtil.storDataBySP(context, userId, GeneralUtil.toJson(orders, new TypeToken<List<Order>>(){}.getType()));
            return true;
        }
        return false;
    }

    @Override
    public boolean paymentByBalance(float amount, UserInfo userInfo, int orderId, String feedback, int rating) {
        if (userInfo != null){
//            List<Order> orders = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, userInfo.getUserId()), new TypeToken<List<Order>>(){}.getType());
//            if (!TextUtils.isEmpty(feedback)){
//                orders.get(orderId).setFeedback(feedback);
//            }
//            orders.get(orderId).setStatus(Constants.STATUS_ORDER_PAID);
//            orders.get(orderId).setRating(rating);
//            float balance = userInfo.getBalance() - amount;
//            userInfo.setBalance(balance);
//         GeneralUtil.storDataBySP(context, userInfo.getUserId(), GeneralUtil.toJson(orders, new TypeToken<List<Order>>(){}.getType()));
//            GeneralUtil.storDataBySP(context, userInfo.getUserName(), GeneralUtil.toJson(userInfo, UserInfo.class));
            return true;
        }
        return false;
    }
}
