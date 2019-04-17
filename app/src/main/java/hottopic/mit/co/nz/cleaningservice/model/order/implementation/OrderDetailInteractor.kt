package hottopic.mit.co.nz.cleaningservice.model.order.implementation

import android.annotation.SuppressLint
import android.content.Context
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import hottopic.mit.co.nz.cleaningservice.listener.OnBooleanRepListener
import hottopic.mit.co.nz.cleaningservice.model.order.inter.IOrderDetailInteractor
import hottopic.mit.co.nz.cleaningservice.network.ServiceStatusChangedRequest

class OrderDetailInteractor(private val context: Context): IOrderDetailInteractor {
    @SuppressLint("CheckResult")
    override fun orderChangeStatus(order: Order, code: Int, onBooleanRepListener: OnBooleanRepListener) {
        ServiceStatusChangedRequest(context, code, order).data.subscribe ({
            response ->
            when{
                response == null -> onBooleanRepListener.onError()
                Constants.RESPONSE_CODE_SUCCESSFUL == response.code -> onBooleanRepListener.onSuccess(response.result, response.message)
                else -> onBooleanRepListener.onNetworkError(response.message)
            }
        }, { onBooleanRepListener.onError() })
    }
}