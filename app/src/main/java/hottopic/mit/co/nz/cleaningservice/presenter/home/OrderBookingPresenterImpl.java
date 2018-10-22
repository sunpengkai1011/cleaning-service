package hottopic.mit.co.nz.cleaningservice.presenter.home;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.home.IOrderBooking;
import hottopic.mit.co.nz.cleaningservice.model.home.OrderBookingModel;
import hottopic.mit.co.nz.cleaningservice.view.home.order.IOrderBookingView;

public class OrderBookingPresenterImpl implements IOrderBookingPresenter {
    private IOrderBookingView iOrderBookingView;
    private IOrderBooking iOrderBooking;

    public OrderBookingPresenterImpl(IOrderBookingView iOrderBookingViewm, Context context) {
        this.iOrderBookingView = iOrderBookingViewm;
        iOrderBooking = new OrderBookingModel(context);
    }

    @Override
    public void getServiceType() {
        List<ServiceType> types = iOrderBooking.getServiceTypes();
        if (types != null){
            iOrderBookingView.getServiceTypeResult(types, Constants.RESPONSE_CODE_SUCCESSFUL);
        }else {
            iOrderBookingView.getServiceTypeResult(types, Constants.RESPONSE_CODE_FAIL);
        }
    }

    @Override
    public void bookingService(Order order, UserInfo userInfo) {
        if (order != null){
            if (iOrderBooking.orderBooking(order, userInfo)){
                iOrderBookingView.bookingResult(Constants.RESPONSE_CODE_SUCCESSFUL);
            }else {
                iOrderBookingView.bookingResult(Constants.RESPONSE_CODE_FAIL);
            }
        }else {
            iOrderBookingView.bookingResult(Constants.RESPONSE_CODE_FAIL);
        }
    }
}
