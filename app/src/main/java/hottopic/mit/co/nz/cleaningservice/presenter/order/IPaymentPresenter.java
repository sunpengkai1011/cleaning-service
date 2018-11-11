package hottopic.mit.co.nz.cleaningservice.presenter.order;

import hottopic.mit.co.nz.cleaningservice.entities.network.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.DiscountsResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.PaymentParams;
import hottopic.mit.co.nz.cleaningservice.entities.network.UserInfoResponse;

public interface IPaymentPresenter {
    void getDiscounts();
    void getDiscoutsResult(DiscountsResponse response);
    void paymentByCard(PaymentParams paymentParams);
    void paymentByBalance(PaymentParams paymentParams);
    void getPaymentBalanceResult(UserInfoResponse response);
    void getPaymentCardResult(BooleanResponse response);
}
