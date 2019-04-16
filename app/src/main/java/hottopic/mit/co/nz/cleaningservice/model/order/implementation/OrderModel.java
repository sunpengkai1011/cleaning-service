package hottopic.mit.co.nz.cleaningservice.model.order.implementation;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.model.order.inter.IOrder;
import hottopic.mit.co.nz.cleaningservice.network.GetOrdersRequest;
import hottopic.mit.co.nz.cleaningservice.network.OrderBookingRequest;
import hottopic.mit.co.nz.cleaningservice.network.ServiceStatusChangedRequest;
import hottopic.mit.co.nz.cleaningservice.network.ServiceTypesRequest;
import hottopic.mit.co.nz.cleaningservice.presenter.order.inter.IOrderPresenter;
import hottopic.mit.co.nz.cleaningservice.presenter.order.implementation.OrderPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.view.order.inter.IOrderView;

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
        presenter = new OrderPresenterImpl(context, iOrderView);
        new GetOrdersRequest(context, Constants.TYPE_GET_ORDERS_STAFF).getData().subscribe(
                getOrdersResponse -> presenter.getOrdersResult(getOrdersResponse),
                e -> presenter.getOrdersResult(null)
        );
    }

    @Override
    public void startedOrder(Order order) {
        presenter = new OrderPresenterImpl(context, iOrderView);
        new ServiceStatusChangedRequest(context, Constants.TYPE_SERVICE_STARTED, order).getData().subscribe(
                booleanResponse -> presenter.orderStatusChangeResult(booleanResponse),
                e -> presenter.orderStatusChangeResult(null)
        );
    }

    @Override
    public void finishedOrder(Order order) {
        presenter = new OrderPresenterImpl(context, iOrderView);
        new ServiceStatusChangedRequest(context, Constants.TYPE_SERVICE_FINISHED, order).getData().subscribe(
                booleanResponse -> presenter.orderStatusChangeResult(booleanResponse),
                e -> presenter.orderStatusChangeResult(null)
        );
    }

    @Override
    public void orderBooking(OrderBookingParams orderBookingParams) {
        presenter = new OrderPresenterImpl(context, iOrderView);
        new OrderBookingRequest(context, orderBookingParams).getData().subscribe(
                booleanResponse -> presenter.orderBookingResult(booleanResponse),
                e -> presenter.orderBookingResult(null)
        );
    }
}
