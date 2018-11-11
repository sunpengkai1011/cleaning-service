package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.entities.network.DiscountsResponse;
import hottopic.mit.co.nz.cleaningservice.network.service.PaymentService;
import io.reactivex.Single;

public class DiscountsRequest extends BaseRequest<PaymentService, DiscountsResponse, DiscountsResponse> {

    public DiscountsRequest(Context context) {
        super(context);
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
    protected Single<DiscountsResponse> getEndpoint(PaymentService endpoint) {
        return endpoint.getDiscouts();
    }

    @Override
    protected DiscountsResponse dealWithResponse(DiscountsResponse response) {
        return response;
    }
}
