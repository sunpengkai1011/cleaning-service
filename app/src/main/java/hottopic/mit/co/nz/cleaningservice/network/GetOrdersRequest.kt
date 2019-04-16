package hottopic.mit.co.nz.cleaningservice.network

import android.content.Context

import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.entities.network.response.GetOrdersResponse
import hottopic.mit.co.nz.cleaningservice.network.service.OrderService
import io.reactivex.Single

class GetOrdersRequest : BaseRequest<OrderService, GetOrdersResponse, GetOrdersResponse> {
    private var userId: Int = 0
    private var type: Int = 0

    override val header: Map<String, String>?
        get() = null

    override val endpointClass: Class<OrderService>
        get() = OrderService::class.java

    constructor(context: Context, user_id: Int, type: Int) : super(context) {
        this.userId = user_id
        this.type = type
    }

    constructor(context: Context, type: Int) : super(context) {
        this.type = type
    }

    override fun getEndpoint(endpoint: OrderService?): Single<GetOrdersResponse?>? {
        return if (Constants.TYPE_GET_ORDERS_CUSTOMER == type) {
            endpoint?.getOrdersByCustomer(userId)
        } else {
            endpoint?.ordersByStaff
        }
    }

    override fun dealWithResponse(response: GetOrdersResponse): GetOrdersResponse? {
        return response
    }
}
