package hottopic.mit.co.nz.cleaningservice.network.service

import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse
import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams
import hottopic.mit.co.nz.cleaningservice.entities.network.response.GetOrdersResponse
import hottopic.mit.co.nz.cleaningservice.entities.network.response.ServiceTypesResponse
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderService {
    @get:GET("cleaning/service")
    val serviceTypes: Single<ServiceTypesResponse?>?

    @get:GET("cleaning/order/staff")
    val ordersByStaff: Single<GetOrdersResponse?>?

    @POST("cleaning/order/booking")
    fun bookingOrder(@Body orderBookingParams: OrderBookingParams): Single<BooleanResponse?>?

    @GET("cleaning/order/customer/{userId}")
    fun getOrdersByCustomer(@Path("userId") user_id: Int): Single<GetOrdersResponse?>?

    @PUT("cleaning/order/started")
    fun orderStarted(@Body order: Order): Single<BooleanResponse?>?

    @PUT("cleaning/order/finished")
    fun orderfinished(@Body order: Order): Single<BooleanResponse?>?
}
