package hottopic.mit.co.nz.cleaningservice.network

import android.content.Context

import hottopic.mit.co.nz.cleaningservice.entities.network.response.ServiceTypesResponse
import hottopic.mit.co.nz.cleaningservice.network.service.OrderService
import io.reactivex.Single

class ServiceTypesRequest(context: Context) : BaseRequest<OrderService, ServiceTypesResponse, ServiceTypesResponse>(context) {

    override val header: Map<String, String>?
        get() = null

    override val endpointClass: Class<OrderService>
        get() = OrderService::class.java

    override fun getEndpoint(endpoint: OrderService?): Single<ServiceTypesResponse?>? {
        return endpoint?.serviceTypes
    }

    override fun dealWithResponse(response: ServiceTypesResponse): ServiceTypesResponse? {
        return response
    }
}
