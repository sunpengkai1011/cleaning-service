package hottopic.mit.co.nz.cleaningservice.presenter.payment;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IPaymentPresenter {
    void paymentByCard(float amount, String cardNo, String userId, int orderId, String feedback);
    void paymentByBalance(float amount, UserInfo userInfo, int orderId, String feedback);
}
