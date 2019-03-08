package hottopic.mit.co.nz.cleaningservice.network.service;

import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.GetOrdersResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.ServiceTypesResponse;
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderService {
    @GET("cleaning/service")
    Single<ServiceTypesResponse> getServiceTypes();

    @POST("cleaning/order/booking")
    Single<BooleanResponse> bookingOrder(@Body OrderBookingParams orderBookingParams);

    @GET("cleaning/order/customer/{user_id}")
    Single<GetOrdersResponse> getOrdersByCustomer(@Path("user_id") int user_id);

    @GET("cleaning/order/staff")
    Single<GetOrdersResponse> getOrdersByStaff();

    @PUT("cleaning/order/started")
    Single<BooleanResponse> orderStarted(@Body Order order);

    @PUT("cleaning/order/finished")
    Single<BooleanResponse> orderfinished(@Body Order order);
}
