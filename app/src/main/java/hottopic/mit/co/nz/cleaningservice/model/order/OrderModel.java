package hottopic.mit.co.nz.cleaningservice.model.order;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class OrderModel implements IOrder {
    private List<Order> orders;
    private Context context;

    public OrderModel(Context context) {
        this.context = context;
    }

    @Override
    public List<Order> getOrders(String userId) {
        return GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, userId), new TypeToken<List<Order>>(){}.getType());
    }

    @Override
    public boolean startedOrder(String userId, int position, String started) {
        orders = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, userId), new TypeToken<List<Order>>(){}.getType());
        if (orders.size() > position) {
            orders.get(position).setStartTime(started);
            orders.get(position).setStatus(Constants.STATUS_ORDER_STARTED);
            GeneralUtil.storDataBySP(context, userId, GeneralUtil.toJson(orders, new TypeToken<List<Order>>(){}.getType()));
            return true;
        }
        return false;
    }

    @Override
    public boolean finishedOrder(String userId, int position, String finished) {
        orders = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, userId), new TypeToken<List<Order>>(){}.getType());
        if (orders.size() > position) {
            Order order = orders.get(position);
            switch (orders.get(position).getServiceType().getTypeId()){
                case Constants.ID_SERVICE_G_CLEANING:
                case Constants.ID_SERVICE_D_CLEANING:
                    order.setEndTime(finished);
                    int duration = GeneralUtil.calculateDuration(order.getStartTime(), orders.get(position).getEndTime());
                    float amount = order.getSubOption().getUnitPrice() * duration;
                    order.setDuration(duration);
                    order.setAmount(amount);
                    break;
            }
            order.setStatus(Constants.STATUS_ORDER_FINISHED);
            GeneralUtil.storDataBySP(context, userId, GeneralUtil.toJson(orders, new TypeToken<List<Order>>(){}.getType()));
            return true;
        }
        return false;
    }

    @Override
    public boolean orderBooking(Order order, String userId) {
        List<Order> orders = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, order.getUserId()), new TypeToken<ArrayList<Order>>(){}.getType());
        if (orders == null){
            orders = new ArrayList<>();
        }
        if (order != null) {
            orders.add(order);
            GeneralUtil.storDataBySP(context, order.getUserId(), GeneralUtil.toJson(orders, new TypeToken<List<Order>>(){}.getType()));
            return true;
        }
        return false;
    }
}
