package hottopic.mit.co.nz.cleaningservice.model.home;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class OrderModel implements IOrder {
    private List<Order> orders;
    private Context context;

    public OrderModel(Context context) {
        this.context = context;
    }

    @Override
    public List<Order> getOrder(UserInfo userInfo) {
        return GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, Constants.SP_KEY_ORDERS), new TypeToken<List<Order>>(){}.getType());
    }

    @Override
    public boolean startedOrder(int position, String started) {
        orders = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, Constants.SP_KEY_ORDERS), new TypeToken<List<Order>>(){}.getType());
        if (orders.size() > position) {
            orders.get(position).setStartTime(started);
            orders.get(position).setOrderStatus(Constants.STATUS_ORDER_STARTED);
            GeneralUtil.storDataBySP(context, Constants.SP_KEY_ORDERS, GeneralUtil.toJson(orders, new TypeToken<List<Order>>(){}.getType()));
            return true;
        }
        return false;
    }

    @Override
    public boolean finishedOrder(int position, String finished) {
        orders = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, Constants.SP_KEY_ORDERS), new TypeToken<List<Order>>(){}.getType());
        if (orders.size() > position) {
            orders.get(position).setEndTime(finished);
            int duration = GeneralUtil.calculateDuration(orders.get(position).getStartTime(), orders.get(position).getEndTime());
            float amount = orders.get(position).getServiceType().getPricePerHour() * duration;
            orders.get(position).setDuration(duration);
            orders.get(position).setAmount(amount);
            orders.get(position).setOrderStatus(Constants.STATUS_ORDER_FINISHED);
            GeneralUtil.storDataBySP(context, Constants.SP_KEY_ORDERS, GeneralUtil.toJson(orders, new TypeToken<List<Order>>(){}.getType()));
            return true;
        }
        return false;
    }
}
