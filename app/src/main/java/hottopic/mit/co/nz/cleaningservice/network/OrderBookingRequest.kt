package hottopic.mit.co.nz.cleaningservice.network

import android.content.Context
import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams
import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse
import hottopic.mit.co.nz.cleaningservice.network.service.OrderService
import io.reactivex.Single

class OrderBookingRequest(context: Context, private val orderBookingParams: OrderBookingParams) : BaseRequest<OrderService, BooleanResponse, BooleanResponse>(context) {

    override val header: Map<String, String>?
        get() = null

    override val endpointClass: Class<OrderService>
        get() = OrderService::class.java

    override fun getEndpoint(endpoint: OrderService?): Single<BooleanResponse?>? {
        return endpoint?.bookingOrder(orderBookingParams)
    }

    override fun dealWithResponse(response: BooleanResponse): BooleanResponse? {
        return response
    }
}
