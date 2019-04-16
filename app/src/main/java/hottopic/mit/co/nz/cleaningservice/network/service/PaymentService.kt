package hottopic.mit.co.nz.cleaningservice.network.service

import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse
import hottopic.mit.co.nz.cleaningservice.entities.network.response.DiscountsResponse
import hottopic.mit.co.nz.cleaningservice.entities.network.params.PaymentParams
import hottopic.mit.co.nz.cleaningservice.entities.network.response.UserInfoResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface PaymentService {

    @get:GET("cleaning/payment/discount")
    val discouts: Single<DiscountsResponse?>?

    @PUT("cleaning/payment/balance")
    fun paymentByBalance(@Body paymentBalance: PaymentParams): Single<UserInfoResponse?>?

    @PUT("cleaning/payment/card")
    fun paymentByCard(@Body paymentBalance: PaymentParams): Single<BooleanResponse?>?
}
