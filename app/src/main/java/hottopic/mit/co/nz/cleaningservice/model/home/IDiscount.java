package hottopic.mit.co.nz.cleaningservice.model.home;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.discounts.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IDiscount {
    UserInfo getUserInfo();
    List<Discount> requestDiscountData();
    boolean topUp(UserInfo userInfo, Discount discount);
}
