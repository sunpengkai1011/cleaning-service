package hottopic.mit.co.nz.cleaningservice.presenter.home;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IOrderPresenter {
    void getOrders(String userId);
    void startedOrder(String userId, int position, String started);
    void finishedOrder(String userId, int position, String finished);
    void bookingService(Order order, String userId);
}
