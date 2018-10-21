package hottopic.mit.co.nz.cleaningservice.presenter.home;

import hottopic.mit.co.nz.cleaningservice.entities.discounts.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IDiscountPresenter {
    void requestDiscouts();
    void topUp(UserInfo userInfo, Discount discount);
}
