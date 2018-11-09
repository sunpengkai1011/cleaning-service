package hottopic.mit.co.nz.cleaningservice.model.order;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;

public interface IOrder {
    void getServiceTypes();
    void getOrders(int userId);
    boolean startedOrder(int userId, int position, String started);
    boolean finishedOrder(int userId, int position, String finished);
    boolean orderBooking(Order order, int userId);
}
