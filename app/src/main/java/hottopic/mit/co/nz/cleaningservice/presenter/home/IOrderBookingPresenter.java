package hottopic.mit.co.nz.cleaningservice.presenter.home;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IOrderBookingPresenter {
    void getServiceType();
    void bookingService(Order order, UserInfo userInfo);
}
