package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams;
import hottopic.mit.co.nz.cleaningservice.network.service.OrderService;
import io.reactivex.Single;

public class OrderBookingRequest extends BaseRequest<OrderService, BooleanResponse, BooleanResponse> {
    private Context context;
    private OrderBookingParams orderBookingParams;

    public OrderBookingRequest(Context context, OrderBookingParams orderBookingParams) {
        super(context);
        this.context = context;
        this.orderBookingParams = orderBookingParams;
    }

    @Override
    protected Map<String, String> getHeader() {
        return null;
    }

    @Override
    protected Class<OrderService> getEndpointClass() {
        return OrderService.class;
    }

    @Override
    protected Single<BooleanResponse> getEndpoint(OrderService endpoint) {
        return endpoint.bookingOrder(orderBookingParams);
    }

    @Override
    protected BooleanResponse dealWithResponse(BooleanResponse response) {
        return response;
    }
}
