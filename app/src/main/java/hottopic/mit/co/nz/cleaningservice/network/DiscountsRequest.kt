package hottopic.mit.co.nz.cleaningservice.network

import android.content.Context

import hottopic.mit.co.nz.cleaningservice.entities.network.response.DiscountsResponse
import hottopic.mit.co.nz.cleaningservice.network.service.PaymentService
import io.reactivex.Single

class DiscountsRequest(context: Context) : BaseRequest<PaymentService, DiscountsResponse, DiscountsResponse>(context) {

    override val header: Map<String, String>?
        get() = null

    override val endpointClass: Class<PaymentService>
        get() = PaymentService::class.java

    override fun getEndpoint(endpoint: PaymentService?): Single<DiscountsResponse?>? {
        return endpoint?.discouts
    }

    override fun dealWithResponse(response: DiscountsResponse): DiscountsResponse? {
        return response
    }
}
