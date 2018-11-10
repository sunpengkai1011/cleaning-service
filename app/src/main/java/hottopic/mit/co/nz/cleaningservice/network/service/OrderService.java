package hottopic.mit.co.nz.cleaningservice.network.service;

import hottopic.mit.co.nz.cleaningservice.entities.network.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.OrderBooking;
import hottopic.mit.co.nz.cleaningservice.entities.network.GetOrdersResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.ServiceTypesResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    @GET("cleaning/service")
    Single<ServiceTypesResponse> getServiceTypes();

    @POST("cleaning/order/booking")
    Single<BooleanResponse> bookingOrder(@Body OrderBooking orderBooking);

    @GET("cleaning/order/customer/{user_id}")
    Single<GetOrdersResponse> getOrdersByCustomer(@Path("user_id") int user_id);

    @GET("cleaning/order/staff")
    Single<GetOrdersResponse> getOrdersByStaff();
}
