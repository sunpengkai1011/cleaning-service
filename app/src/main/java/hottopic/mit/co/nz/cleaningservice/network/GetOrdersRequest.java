package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.network.GetOrdersResponse;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.network.service.OrderService;
import io.reactivex.Single;

public class GetOrdersRequest extends BaseRequest<OrderService, GetOrdersResponse, GetOrdersResponse> {
    private int user_id;
    private int type;

    public GetOrdersRequest(Context context, int user_id, int type) {
        super(context);
        this.user_id = user_id;
        this.type = type;
    }

    public GetOrdersRequest(Context context, int type) {
        super(context);
        this.type = type;
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
    protected Single<GetOrdersResponse> getEndpoint(OrderService endpoint) {
        if (Constants.TYPE_GET_ORDERS_CUSTOMER == type){
            return endpoint.getOrdersByCustomer(user_id);
        }else{
            return endpoint.getOrdersByStaff();
        }
    }

    @Override
    protected GetOrdersResponse dealWithResponse(GetOrdersResponse response) {
        return response;
    }
}
