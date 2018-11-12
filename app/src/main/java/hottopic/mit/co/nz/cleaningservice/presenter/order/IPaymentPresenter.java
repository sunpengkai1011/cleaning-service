package hottopic.mit.co.nz.cleaningservice.presenter.order;

import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.DiscountsResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.params.PaymentParams;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.UserInfoResponse;

public interface IPaymentPresenter {
    void getDiscounts();
    void getDiscoutsResult(DiscountsResponse response);
    void paymentByCard(PaymentParams paymentParams);
    void paymentByBalance(PaymentParams paymentParams);
    void getPaymentBalanceResult(UserInfoResponse response);
    void getPaymentCardResult(BooleanResponse response);
}
