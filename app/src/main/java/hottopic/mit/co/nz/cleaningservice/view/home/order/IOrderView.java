package hottopic.mit.co.nz.cleaningservice.view.home.order;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;

public interface IOrderView {
   void getOrdersResult(List<Order> orders, int code);
   void getStartedResult(int code);
   void getFinishedResult(int code);
   void bookingResult(int code);
}
