package hottopic.mit.co.nz.cleaningservice.network

import android.content.Context

import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import hottopic.mit.co.nz.cleaningservice.network.service.OrderService
import io.reactivex.Single

class ServiceStatusChangedRequest(context: Context, private val type: Int, private val order: Order) : BaseRequest<OrderService, BooleanResponse, BooleanResponse>(context) {

    override val header: Map<String, String>?
        get() = null

    override val endpointClass: Class<OrderService>
        get() = OrderService::class.java

    override fun getEndpoint(endpoint: OrderService?): Single<BooleanResponse?>? {
        return if (Constants.TYPE_SERVICE_STARTED == type) {
            endpoint?.orderStarted(order)
        } else {
            endpoint?.orderfinished(order)
        }
    }

    override fun dealWithResponse(response: BooleanResponse): BooleanResponse? {
        return response
    }
}
