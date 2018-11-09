package hottopic.mit.co.nz.cleaningservice.presenter.order;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IDiscountPresenter {
    void requestDiscouts();
    void topUp(UserInfo userInfo, Discount discount);
}
