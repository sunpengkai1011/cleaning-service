package hottopic.mit.co.nz.cleaningservice.network

import android.content.Context

import hottopic.mit.co.nz.cleaningservice.entities.network.params.PaymentParams
import hottopic.mit.co.nz.cleaningservice.entities.network.response.UserInfoResponse
import hottopic.mit.co.nz.cleaningservice.network.service.PaymentService
import io.reactivex.Single

class PaymentByBalanceRequest(context: Context, private val paymentParams: PaymentParams) : BaseRequest<PaymentService, UserInfoResponse, UserInfoResponse>(context) {

    override val header: Map<String, String>?
        get() = null

    override val endpointClass: Class<PaymentService>
        get() = PaymentService::class.java

    override fun getEndpoint(endpoint: PaymentService?): Single<UserInfoResponse?>? {
        return endpoint?.paymentByBalance(paymentParams)
    }

    override fun dealWithResponse(response: UserInfoResponse): UserInfoResponse? {
        return response
    }
}
