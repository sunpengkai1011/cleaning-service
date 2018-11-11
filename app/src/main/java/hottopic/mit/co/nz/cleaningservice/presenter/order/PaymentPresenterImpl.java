package hottopic.mit.co.nz.cleaningservice.presenter.order;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.network.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.DiscountsResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.PaymentParams;
import hottopic.mit.co.nz.cleaningservice.entities.network.UserInfoResponse;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.payment.IPayment;
import hottopic.mit.co.nz.cleaningservice.model.payment.PaymentModel;
import hottopic.mit.co.nz.cleaningservice.view.order.IPaymentView;

public class PaymentPresenterImpl implements IPaymentPresenter {
    private Context context;
    private IPaymentView iPaymentView;
    private IPayment iPayment;

    public PaymentPresenterImpl(Context context, IPaymentView iPaymentView) {
        this.context = context;
        this.iPaymentView = iPaymentView;
    }

    @Override
    public void getDiscounts() {
        iPayment = new PaymentModel(context, iPaymentView);
        iPayment.getDiscounts();
    }

    @Override
    public void getDiscoutsResult(DiscountsResponse response) {
        if (response == null){
            iPaymentView.getDiscountsResult(null, "Get discounts failed");
        }else {
            if (Constants.RESPONSE_CODE_SUCCESSFUL == response.getCode()){
                iPaymentView.getDiscountsResult(response.getDiscounts(), response.getMessage());
            }else{
                iPaymentView.getDiscountsResult(null, response.getMessage());
            }
        }
    }

    @Override
    public void paymentByCard(PaymentParams paymentParams) {
        iPayment = new PaymentModel(context, iPaymentView);
        iPayment.paymentByCard(paymentParams);
    }

    @Override
    public void paymentByBalance(PaymentParams paymentParams) {
        iPayment = new PaymentModel(context, iPaymentView);
        iPayment.paymentByBalance(paymentParams);

    }

    @Override
    public void getPaymentBalanceResult(UserInfoResponse response) {
        if (response == null){
            iPaymentView.getPaymentBalanceResult(null, "Payment is failed", 0);
        }else{
            if (Constants.RESPONSE_CODE_SUCCESSFUL == response.getCode()) {
                iPaymentView.getPaymentBalanceResult(response.getUserInfo().get(0), response.getMessage(), response.getType());
            }else{
                iPaymentView.getPaymentBalanceResult(null, response.getMessage(), 0);
            }
        }
    }

    @Override
    public void getPaymentCardResult(BooleanResponse response) {
        if (response == null){
            iPaymentView.getPaymentCardResult(false, "Payment is failed");
        }else{
            iPaymentView.getPaymentCardResult(response.getResult(), response.getMessage());
        }
    }
}
