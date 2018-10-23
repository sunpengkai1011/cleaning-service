package hottopic.mit.co.nz.cleaningservice.presenter.payment;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.payment.IPayment;
import hottopic.mit.co.nz.cleaningservice.model.payment.PaymentModel;
import hottopic.mit.co.nz.cleaningservice.view.payment.IPaymentView;

public class PaymentPresenterImpl implements IPaymentPresenter {
    private Context context;
    private IPaymentView iPaymentView;
    private IPayment iPayment;

    public PaymentPresenterImpl(Context context, IPaymentView iPaymentView) {
        this.context = context;
        this.iPaymentView = iPaymentView;
    }

    @Override
    public void paymentByCard(float amount, String cardNo, int orderId, String feedback) {
        iPayment = new PaymentModel(context);
        if (iPayment.paymentByCard(amount, cardNo,orderId, feedback)){
            iPaymentView.getPaymentResult(Constants.TYPE_PAYMENT_CARD, Constants.RESPONSE_CODE_SUCCESSFUL);
        }else {
            iPaymentView.getPaymentResult(Constants.TYPE_PAYMENT_CARD, Constants.RESPONSE_CODE_FAIL);
        }
    }

    @Override
    public void paymentByBalance(float amount, UserInfo userInfo, int orderId, String feedback) {
        iPayment = new PaymentModel(context);
        if (iPayment.paymentByBalance(amount, userInfo,orderId, feedback)){
            iPaymentView.getPaymentResult(Constants.TYPE_PAYMENT_BALANCE, Constants.RESPONSE_CODE_SUCCESSFUL);
        }else {
            iPaymentView.getPaymentResult(Constants.TYPE_PAYMENT_BALANCE, Constants.RESPONSE_CODE_FAIL);
        }
    }
}
