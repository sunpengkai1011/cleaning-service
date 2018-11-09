package hottopic.mit.co.nz.cleaningservice.network.service;

import hottopic.mit.co.nz.cleaningservice.entities.network.ServiceTypesResponse;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface OrderService {
    @GET("cleaning/service/types}")
    Single<ServiceTypesResponse> getServiceTypes();
}
