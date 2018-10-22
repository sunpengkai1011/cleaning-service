package hottopic.mit.co.nz.cleaningservice.model.home;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IOrder {
    List<Order> getOrder(UserInfo userInfo);
    boolean startedOrder(int position, String started);
    boolean finishedOrder(int position, String finished);
}
