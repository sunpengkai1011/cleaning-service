package hottopic.mit.co.nz.cleaningservice.presenter.order;

import android.content.Context;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.model.order.IOrder;
import hottopic.mit.co.nz.cleaningservice.model.order.OrderModel;
import hottopic.mit.co.nz.cleaningservice.view.order.IOrderView;

public class OrderPresenterImpl implements IOrderPresenter{
    private IOrder iOrder;
    private IOrderView iOrderView;

    public OrderPresenterImpl(Context context, IOrderView iOrderView) {
        this.iOrderView = iOrderView;
        iOrder = new OrderModel(context);
    }

    @Override
    public void getOrders(int userInfo) {
//        List<Order> orders = iOrder.getOrders(userInfo);
//        if (orders != null && orders.size() > 0){
//            iOrderView.getOrdersResult(orders, Constants.RESPONSE_CODE_SUCCESSFUL);
//        }else{
//            iOrderView.getOrdersResult(orders, Constants.RESPONSE_CODE_FAIL);
//        }
    }

    @Override
    public void startedOrder(int userId, int position, String started) {
        if (iOrder.startedOrder(userId, position, started)){
            iOrderView.getStartedResult(Constants.RESPONSE_CODE_SUCCESSFUL);
        }else{
            iOrderView.getStartedResult(Constants.RESPONSE_CODE_FAIL);
        }
    }

    @Override
    public void finishedOrder(int userId, int position, String finished) {
        if (iOrder.finishedOrder(userId, position, finished)){
            iOrderView.getFinishedResult(Constants.RESPONSE_CODE_SUCCESSFUL);
        }else{
            iOrderView.getFinishedResult(Constants.RESPONSE_CODE_FAIL);
        }
    }

    @Override
    public void bookingService(Order order, int userId) {
        if (order != null){
            if (iOrder.orderBooking(order, userId)){
                iOrderView.bookingResult(Constants.RESPONSE_CODE_SUCCESSFUL);
            }else {
                iOrderView.bookingResult(Constants.RESPONSE_CODE_FAIL);
            }
        }else {
            iOrderView.bookingResult(Constants.RESPONSE_CODE_FAIL);
        }
    }
}
