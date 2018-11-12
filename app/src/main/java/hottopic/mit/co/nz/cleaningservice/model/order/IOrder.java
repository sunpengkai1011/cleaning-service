package hottopic.mit.co.nz.cleaningservice.model.order;

import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;

public interface IOrder {
    void getServiceTypes();
    void getOrdersByCustomer(int userId);
    void getOrdersByStaff();
    void startedOrder(Order order);
    void finishedOrder(Order order);
    void orderBooking(OrderBookingParams orderBookingParams);
}
