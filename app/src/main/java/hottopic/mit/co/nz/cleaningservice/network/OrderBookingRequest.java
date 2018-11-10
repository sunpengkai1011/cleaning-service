package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.entities.network.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.OrderBooking;
import hottopic.mit.co.nz.cleaningservice.network.service.OrderService;
import io.reactivex.Single;

public class OrderBookingRequest extends BaseRequest<OrderService, BooleanResponse, BooleanResponse> {
    private Context context;
    private OrderBooking orderBooking;

    public OrderBookingRequest(Context context, OrderBooking orderBooking) {
        super(context);
        this.context = context;
        this.orderBooking = orderBooking;
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
        return endpoint.bookingOrder(orderBooking);
    }

    @Override
    protected BooleanResponse dealWithResponse(BooleanResponse response) {
        return response;
    }
}
