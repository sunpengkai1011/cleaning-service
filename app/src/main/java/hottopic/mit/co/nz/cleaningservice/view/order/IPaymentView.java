package hottopic.mit.co.nz.cleaningservice.view.order;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IPaymentView {
    void getDiscountsResult(List<Discount> discounts, String message);
    void getPaymentBalanceResult(UserInfo userInfo, String message, int type);
    void getPaymentCardResult(boolean result, String message);
}
