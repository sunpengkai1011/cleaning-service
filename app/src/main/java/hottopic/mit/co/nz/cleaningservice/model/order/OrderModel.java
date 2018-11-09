package hottopic.mit.co.nz.cleaningservice.model.order;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.network.ServiceTypesRequest;
import hottopic.mit.co.nz.cleaningservice.presenter.home.HomePresenterImpl;
import hottopic.mit.co.nz.cleaningservice.presenter.home.IHomePresenter;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.view.home.IHomeView;

public class OrderModel implements IOrder {
    private List<Order> orders;
    private Context context;
    private IHomePresenter presenter;

    public OrderModel(Context context){
        this.context = context;
    }

    public OrderModel(Context context, IHomeView iHomeView) {
        this.context = context;
        presenter = new HomePresenterImpl(context, iHomeView);
    }

    @Override
    public void getServiceTypes() {
        new ServiceTypesRequest(context).getData().subscribe(
                serviceTypesResponse -> presenter.serviceTypesResult(serviceTypesResponse),
                e -> presenter.serviceTypesResult(null));
    }

    @Override
    public void getOrders(int userId) {
    }

    @Override
    public boolean startedOrder(int userId, int position, String started) {
        if (orders.size() > position) {
            orders.get(position).setStartTime(started);
            orders.get(position).setStatus(Constants.STATUS_ORDER_STARTED);
            return true;
        }
        return false;
    }

    @Override
    public boolean finishedOrder(int userId, int position, String finished) {
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
            return true;
        }
        return false;
    }

    @Override
    public boolean orderBooking(Order order, int userId) {
        if (orders == null){
            orders = new ArrayList<>();
        }
        if (order != null) {
            orders.add(order);
            return true;
        }
        return false;
    }
}
