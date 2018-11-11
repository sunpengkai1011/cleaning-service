package hottopic.mit.co.nz.cleaningservice.model.payment;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.network.DiscountsResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.PaymentParams;
import hottopic.mit.co.nz.cleaningservice.entities.network.UserInfoResponse;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.network.DiscountsRequest;
import hottopic.mit.co.nz.cleaningservice.network.GetOrdersRequest;
import hottopic.mit.co.nz.cleaningservice.network.PaymentByBalanceRequest;
import hottopic.mit.co.nz.cleaningservice.network.PaymentByCardRequest;
import hottopic.mit.co.nz.cleaningservice.presenter.order.IPaymentPresenter;
import hottopic.mit.co.nz.cleaningservice.presenter.order.PaymentPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.view.order.IPaymentView;

public class PaymentModel implements IPayment {
    private Context context;
    private IPaymentPresenter presenter;
    private IPaymentView iPaymentView;
    public PaymentModel(Context context, IPaymentView iPaymentView) {
        this.context = context;
        this.iPaymentView = iPaymentView;
    }

    @Override
    public void getDiscounts() {
        presenter = new PaymentPresenterImpl(context, iPaymentView);
        new DiscountsRequest(context).getData().subscribe(
                discountsResponse -> presenter.getDiscoutsResult(discountsResponse),
                e -> presenter.getDiscoutsResult(null)
        );
    }

    @Override
    public void paymentByCard(PaymentParams paymentParams) {
        presenter = new PaymentPresenterImpl(context, iPaymentView);
        new PaymentByCardRequest(context, paymentParams).getData().subscribe(
                booleanResponse -> presenter.getPaymentCardResult(booleanResponse),
                e -> presenter.getPaymentCardResult(null)
        );
    }

    @Override
    public void paymentByBalance(PaymentParams paymentParams) {
        presenter = new PaymentPresenterImpl(context, iPaymentView);
        new PaymentByBalanceRequest(context, paymentParams).getData().subscribe(
                userInfoResponse -> presenter.getPaymentBalanceResult(userInfoResponse),
                e -> presenter.getPaymentBalanceResult(null)
        );
    }

}
