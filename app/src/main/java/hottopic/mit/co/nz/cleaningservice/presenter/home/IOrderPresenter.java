package hottopic.mit.co.nz.cleaningservice.presenter.home;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IOrderPresenter {
    List<Order> getOrder(UserInfo userInfo);
}
