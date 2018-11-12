package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.network.service.OrderService;
import io.reactivex.Single;

public class ServiceStatusChangedRequest extends BaseRequest<OrderService, BooleanResponse, BooleanResponse> {
    private int type;
    private Order order;

    public ServiceStatusChangedRequest(Context context, int type, Order order) {
        super(context);
        this.type = type;
        this.order = order;
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
        if (Constants.TYPE_SERVICE_STARTED == type) {
            return endpoint.orderStarted(order);
        }else{
            return endpoint.orderfinished(order);
        }
    }

    @Override
    protected BooleanResponse dealWithResponse(BooleanResponse response) {
        return response;
    }
}
