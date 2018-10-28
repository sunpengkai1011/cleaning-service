package hottopic.mit.co.nz.cleaningservice.model.order;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IOrder {
    List<Order> getOrders(String userId);
    boolean startedOrder(String userId, int position, String started);
    boolean finishedOrder(String userId, int position, String finished);
    boolean orderBooking(Order order, String userId);
}
