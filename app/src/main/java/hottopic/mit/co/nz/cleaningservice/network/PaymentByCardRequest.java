package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.entities.network.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.PaymentParams;
import hottopic.mit.co.nz.cleaningservice.network.service.PaymentService;
import io.reactivex.Single;

public class PaymentByCardRequest extends BaseRequest<PaymentService, BooleanResponse, BooleanResponse> {
    private PaymentParams paymentParams;

    public PaymentByCardRequest(Context context, PaymentParams paymentParams) {
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
    protected Single<BooleanResponse> getEndpoint(PaymentService endpoint) {
        return endpoint.paymentByCard(paymentParams);
    }

    @Override
    protected BooleanResponse dealWithResponse(BooleanResponse response) {
        return response;
    }
}
