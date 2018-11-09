package hottopic.mit.co.nz.cleaningservice.view.order;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IDiscountView {
    void getDiscountResult(List<Discount> discounts, int code);
    void getTopUpResult(UserInfo userInfo, int code);
}
