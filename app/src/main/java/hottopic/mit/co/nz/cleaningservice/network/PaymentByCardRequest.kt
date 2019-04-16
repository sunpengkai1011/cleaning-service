package hottopic.mit.co.nz.cleaningservice.network

import android.content.Context

import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse
import hottopic.mit.co.nz.cleaningservice.entities.network.params.PaymentParams
import hottopic.mit.co.nz.cleaningservice.network.service.PaymentService
import io.reactivex.Single

class PaymentByCardRequest(context: Context, private val paymentParams: PaymentParams) : BaseRequest<PaymentService, BooleanResponse, BooleanResponse>(context) {

    override val header: Map<String, String>?
        get() = null

    override val endpointClass: Class<PaymentService>
        get() = PaymentService::class.java

    override fun getEndpoint(endpoint: PaymentService?): Single<BooleanResponse?>? {
        return endpoint?.paymentByCard(paymentParams)
    }

    override fun dealWithResponse(response: BooleanResponse): BooleanResponse? {
        return response
    }
}
