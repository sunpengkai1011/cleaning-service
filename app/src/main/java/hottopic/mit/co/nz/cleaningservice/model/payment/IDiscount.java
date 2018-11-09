package hottopic.mit.co.nz.cleaningservice.model.payment;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IDiscount {
    UserInfo getUserInfo(String username);
    List<Discount> requestDiscountData();
    boolean topUp(UserInfo userInfo, Discount discount);
}
