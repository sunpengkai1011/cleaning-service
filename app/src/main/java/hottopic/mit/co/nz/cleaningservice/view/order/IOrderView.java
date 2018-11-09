package hottopic.mit.co.nz.cleaningservice.view.order;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;

public interface IOrderView {
   void getOrdersResult(List<Order> orders, int code);
   void getStartedResult(int code);
   void getFinishedResult(int code);
   void bookingResult(int code);
}
