package hottopic.mit.co.nz.cleaningservice.presenter.order;

import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.GetOrdersResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.ServiceTypesResponse;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;

public interface IOrderPresenter {
    void getOrdersByCustomer(int userId);
    void getOrdersByStaff();
    void startedOrder(Order order);
    void finishedOrder(Order order);
    void orderBooking(OrderBookingParams orderBookingParams);
    void getOrdersResult(GetOrdersResponse response);
    void orderStatusChangeResult(BooleanResponse response);
    void orderBookingResult(BooleanResponse response);
    void getServiceTypes();
    void serviceTypesResult(ServiceTypesResponse response);
}
