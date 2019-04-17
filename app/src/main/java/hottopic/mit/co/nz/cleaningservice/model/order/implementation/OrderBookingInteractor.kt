package hottopic.mit.co.nz.cleaningservice.model.order.implementation

import android.annotation.SuppressLint
import android.content.Context
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams
import hottopic.mit.co.nz.cleaningservice.listener.OnBooleanRepListener
import hottopic.mit.co.nz.cleaningservice.model.order.inter.IOrderBookingInteractor
import hottopic.mit.co.nz.cleaningservice.network.OrderBookingRequest

class OrderBookingInteractor(private val context: Context): IOrderBookingInteractor {
    @SuppressLint("CheckResult")
    override fun orderBooking(orderBookingParams: OrderBookingParams, onBooleanRepListener: OnBooleanRepListener) {
        OrderBookingRequest(context, orderBookingParams).data.subscribe ({
            response ->
            when{
                response == null -> onBooleanRepListener.onError()
                Constants.RESPONSE_CODE_SUCCESSFUL == response.code -> onBooleanRepListener.onSuccess(response.result, response.message)
                else -> onBooleanRepListener.onNetworkError(response.message)
            }
        }, { onBooleanRepListener.onError() })
    }
}