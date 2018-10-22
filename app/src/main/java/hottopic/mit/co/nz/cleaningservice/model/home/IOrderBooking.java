package hottopic.mit.co.nz.cleaningservice.model.home;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IOrderBooking {
    boolean orderBooking(Order order, UserInfo userInfo);
    List<ServiceType> getServiceTypes();
}
