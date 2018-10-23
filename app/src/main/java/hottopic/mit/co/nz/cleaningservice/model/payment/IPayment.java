package hottopic.mit.co.nz.cleaningservice.model.payment;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IPayment {
    boolean paymentByCard(float amount, String cardNo, int orderId, String feedback);
    boolean paymentByBalance(float amount, UserInfo userInfo, int orderId, String feedback);
}
