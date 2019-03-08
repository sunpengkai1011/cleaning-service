package hottopic.mit.co.nz.cleaningservice.model.payment;

import hottopic.mit.co.nz.cleaningservice.entities.network.params.PaymentParams;

public interface IPayment {
    void getDiscounts();
    void paymentByCard(PaymentParams paymentParams);
    void paymentByBalance(PaymentParams paymentParams);
}
