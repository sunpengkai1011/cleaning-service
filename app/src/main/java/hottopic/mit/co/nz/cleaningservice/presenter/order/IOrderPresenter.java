package hottopic.mit.co.nz.cleaningservice.presenter.order;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IOrderPresenter {
    void getOrders(int userId);
    void startedOrder(int userId, int position, String started);
    void finishedOrder(int userId, int position, String finished);
    void bookingService(Order order, int userId);
}
