package hottopic.mit.co.nz.cleaningservice.view.order;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes;

public interface IOrderView {
   void getOrdersResult(List<Order> orders, String message);
   void orderStatusChangeResult(Order order, String message);
   void bookingResult(boolean result, String message);
   void getServiceError(String message);
   void getServiceTypes(ServiceTypes serviceTypes);
}
