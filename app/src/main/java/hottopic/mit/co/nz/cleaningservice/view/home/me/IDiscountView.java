package hottopic.mit.co.nz.cleaningservice.view.home.me;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.discounts.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IDiscountView {
    void getDiscountResult(List<Discount> discounts, int code);
    void getTopUpResult(UserInfo userInfo, int code);
}
