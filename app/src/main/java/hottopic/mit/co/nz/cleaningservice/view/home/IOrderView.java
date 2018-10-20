package hottopic.mit.co.nz.cleaningservice.view.home;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;

public interface IOrderView {
   void getOrdersResult(List<Order> orders, int code);
}
