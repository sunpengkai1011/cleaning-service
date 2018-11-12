package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.entities.network.params.PaymentParams;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.UserInfoResponse;
import hottopic.mit.co.nz.cleaningservice.network.service.PaymentService;
import io.reactivex.Single;

public class PaymentByBalanceRequest extends BaseRequest<PaymentService, UserInfoResponse, UserInfoResponse> {
    private PaymentParams paymentParams;

    public PaymentByBalanceRequest(Context context, PaymentParams paymentParams) {
        super(context);
        this.paymentParams = paymentParams;
    }

    @Override
    protected Map<String, String> getHeader() {
        return null;
    }

    @Override
    protected Class<PaymentService> getEndpointClass() {
        return PaymentService.class;
    }

    @Override
    protected Single<UserInfoResponse> getEndpoint(PaymentService endpoint) {
        return endpoint.paymentByBalance(paymentParams);
    }

    @Override
    protected UserInfoResponse dealWithResponse(UserInfoResponse response) {
        return response;
    }
}
