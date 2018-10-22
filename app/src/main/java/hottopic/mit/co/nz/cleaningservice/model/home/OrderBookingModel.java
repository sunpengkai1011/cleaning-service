package hottopic.mit.co.nz.cleaningservice.model.home;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class OrderBookingModel implements IOrderBooking {
    private Context context;

    public OrderBookingModel(Context context) {
        this.context = context;
    }

    @Override
    public boolean orderBooking(Order order, UserInfo userInfo) {
        List<Order> orders = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, Constants.SP_KEY_ORDERS), new TypeToken<ArrayList<Order>>(){}.getType());
        if (orders == null){
            orders = new ArrayList<>();
        }
        if (order != null) {
            orders.add(order);
            GeneralUtil.storDataBySP(context, Constants.SP_KEY_ORDERS, GeneralUtil.toJson(orders, new TypeToken<List<Order>>(){}.getType()));
            return true;
        }
        return false;
    }

    @Override
    public List<ServiceType> getServiceTypes() {
        return fakeData();
    }

    private List<ServiceType> fakeData(){
        List<ServiceType> typeList = new ArrayList<>();
        ServiceType type = new ServiceType(1, "General Cleaning", 40);
        ServiceType type1 = new ServiceType(2, "Deep Cleaning", 60);
        ServiceType type2 = new ServiceType(3, "General Iron", 20);
        ServiceType type3 = new ServiceType(4, "Special Iron", 40);
        typeList.add(type);
        typeList.add(type1);
        typeList.add(type2);
        typeList.add(type3);
        return typeList;
    }
}
