package hottopic.mit.co.nz.cleaningservice.presenter.home;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.home.IOrder;
import hottopic.mit.co.nz.cleaningservice.model.home.OrderModel;
import hottopic.mit.co.nz.cleaningservice.view.home.order.IOrderDetailView;
import hottopic.mit.co.nz.cleaningservice.view.home.order.IOrderView;

public class OrderPresenterImpl implements IOrderPresenter{
    private IOrder iOrder;
    private IOrderView iOrderView;
    private IOrderDetailView iOrderDetailView;

    public OrderPresenterImpl(Context context, IOrderView iOrderView) {
        this.iOrderView = iOrderView;
        iOrder = new OrderModel(context);
    }

    public OrderPresenterImpl(Context context, IOrderDetailView iOrderDetailView) {
        this.iOrder = new OrderModel(context);
        this.iOrderDetailView = iOrderDetailView;
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
            iOrderDetailView.getStartedResult(Constants.RESPONSE_CODE_SUCCESSFUL);
        }else{
            iOrderDetailView.getStartedResult(Constants.RESPONSE_CODE_FAIL);
        }
    }

    @Override
    public void finishedOrder(int position, String finished) {
        if (iOrder.finishedOrder(position, finished)){
            iOrderDetailView.getFinishedResult(Constants.RESPONSE_CODE_SUCCESSFUL);
        }else{
            iOrderDetailView.getFinishedResult(Constants.RESPONSE_CODE_FAIL);
        }
    }
}
