package hottopic.mit.co.nz.cleaningservice.model.order.inter;

import hottopic.mit.co.nz.cleaningservice.entities.network.params.PaymentParams;

public interface IPayment {
    void getDiscounts();
    void paymentByCard(PaymentParams paymentParams);
    void paymentByBalance(PaymentParams paymentParams);
}
