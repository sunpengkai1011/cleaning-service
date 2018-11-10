package hottopic.mit.co.nz.cleaningservice.model.order;

import hottopic.mit.co.nz.cleaningservice.entities.network.OrderBooking;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;

public interface IOrder {
    void getServiceTypes();
    void getOrdersByCustomer(int userId);
    void getOrdersByStaff();
    void startedOrder(int orderId, String started);
    void finishedOrder(int orderId, String finished);
    void orderBooking(OrderBooking orderBooking);
}
