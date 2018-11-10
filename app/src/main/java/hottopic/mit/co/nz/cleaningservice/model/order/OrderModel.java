package hottopic.mit.co.nz.cleaningservice.model.order;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.network.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.GetOrdersResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.OrderBooking;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.network.GetOrdersRequest;
import hottopic.mit.co.nz.cleaningservice.network.OrderBookingRequest;
import hottopic.mit.co.nz.cleaningservice.network.ServiceTypesRequest;
import hottopic.mit.co.nz.cleaningservice.presenter.order.IOrderPresenter;
import hottopic.mit.co.nz.cleaningservice.presenter.order.OrderPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.view.order.IOrderView;

public class OrderModel implements IOrder {
    private Context context;
    private IOrderView iOrderView;
    private IOrderPresenter presenter;

    public OrderModel(Context context, IOrderView iOrderView) {
        this.context = context;
        this.iOrderView = iOrderView;
    }

    @Override
    public void getServiceTypes() {
        presenter = new OrderPresenterImpl(context, iOrderView);
        new ServiceTypesRequest(context).getData().subscribe(
                serviceTypesResponse -> presenter.serviceTypesResult(serviceTypesResponse),
                e -> presenter.serviceTypesResult(null));
    }

    @Override
    public void getOrdersByCustomer(int userId) {
        presenter = new OrderPresenterImpl(context, iOrderView);
        new GetOrdersRequest(context, userId, Constants.TYPE_GET_ORDERS_CUSTOMER).getData().subscribe(
                getOrdersResponse -> presenter.getOrdersResult(getOrdersResponse),
                e -> presenter.getOrdersResult(null)
        );
    }

    @Override
    public void getOrdersByStaff() {
        new GetOrdersRequest(context, Constants.TYPE_GET_ORDERS_STAFF).getData().subscribe(
                getOrdersResponse -> presenter.getOrdersResult(getOrdersResponse),
                e -> presenter.getOrdersResult(null)
        );
    }

    @Override
    public void startedOrder(int orderId, String started) {
    }

    @Override
    public void finishedOrder(int orderId, String finished) {
    }

    @Override
    public void orderBooking(OrderBooking orderBooking) {
        presenter = new OrderPresenterImpl(context, iOrderView);
        new OrderBookingRequest(context, orderBooking).getData().subscribe(
                booleanResponse -> presenter.orderBookingResult(booleanResponse),
                e -> presenter.orderBookingResult(null)
        );
    }
}
