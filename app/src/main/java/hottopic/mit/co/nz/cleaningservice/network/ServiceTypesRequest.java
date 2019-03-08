package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.entities.network.response.ServiceTypesResponse;
import hottopic.mit.co.nz.cleaningservice.network.service.OrderService;
import io.reactivex.Single;

public class ServiceTypesRequest extends BaseRequest<OrderService, ServiceTypesResponse, ServiceTypesResponse> {
    private Context context;

    public ServiceTypesRequest(Context context) {
        super(context);
        this.context = context;
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
    protected Single<ServiceTypesResponse> getEndpoint(OrderService endpoint) {
        return endpoint.getServiceTypes();
    }

    @Override
    protected ServiceTypesResponse dealWithResponse(ServiceTypesResponse response) {
        return response;
    }
}
