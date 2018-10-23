package hottopic.mit.co.nz.cleaningservice.presenter.home;

import android.content.Context;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.order.IOrder;
import hottopic.mit.co.nz.cleaningservice.model.order.OrderModel;
import hottopic.mit.co.nz.cleaningservice.view.home.order.IOrderView;

public class OrderPresenterImpl implements IOrderPresenter{
    private IOrder iOrder;
    private IOrderView iOrderView;

    public OrderPresenterImpl(Context context, IOrderView iOrderView) {
        this.iOrderView = iOrderView;
        iOrder = new OrderModel(context);
    }

    @Override
    public void getOrder(UserInfo userInfo) {
        List<Order> orders = iOrder.getOrder(userInfo);
        if (orders != null && orders.size() > 0){
            iOrderView.getOrdersResult(orders, Constants.RESPONSE_CODE_SUCCESSFUL);
        }else{
            iOrderView.getOrdersResult(orders, Constants.RESPONSE_CODE_FAIL);
        }
    }

    @Override
    public void startedOrder(int position, String started) {
        if (iOrder.startedOrder(position, started)){
            iOrderView.getStartedResult(Constants.RESPONSE_CODE_SUCCESSFUL);
        }else{
            iOrderView.getStartedResult(Constants.RESPONSE_CODE_FAIL);
        }
    }

    @Override
    public void finishedOrder(int position, String finished) {
        if (iOrder.finishedOrder(position, finished)){
            iOrderView.getFinishedResult(Constants.RESPONSE_CODE_SUCCESSFUL);
        }else{
            iOrderView.getFinishedResult(Constants.RESPONSE_CODE_FAIL);
        }
    }

    @Override
    public void getServiceType() {
        List<ServiceType> types = iOrder.getServiceTypes();
        if (types != null){
            iOrderView.getServiceTypeResult(types, Constants.RESPONSE_CODE_SUCCESSFUL);
        }else {
            iOrderView.getServiceTypeResult(types, Constants.RESPONSE_CODE_FAIL);
        }
    }

    @Override
    public void bookingService(Order order, UserInfo userInfo) {
        if (order != null){
            if (iOrder.orderBooking(order, userInfo)){
                iOrderView.bookingResult(Constants.RESPONSE_CODE_SUCCESSFUL);
            }else {
                iOrderView.bookingResult(Constants.RESPONSE_CODE_FAIL);
            }
        }else {
            iOrderView.bookingResult(Constants.RESPONSE_CODE_FAIL);
        }
    }
}
