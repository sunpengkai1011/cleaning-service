package hottopic.mit.co.nz.cleaningservice.model.order.implementation

import android.annotation.SuppressLint
import android.content.Context
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.listener.OnOrdersListener
import hottopic.mit.co.nz.cleaningservice.model.order.inter.IOrdersInteractor
import hottopic.mit.co.nz.cleaningservice.network.GetOrdersRequest
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class OrdersInteractor(private val context: Context) : IOrdersInteractor {
    @SuppressLint("CheckResult")
    override fun getOrdersByCustomer(userId: Int, onOrdersListener: OnOrdersListener) {
        GetOrdersRequest(context, userId, Constants.TYPE_GET_ORDERS_CUSTOMER).data.subscribe({ response ->
            when {
                response == null -> onOrdersListener.onError()
                Constants.RESPONSE_CODE_SUCCESSFUL == response.code ->
                    Single.just(GeneralUtil.sortingOrders(response.orderResponses))
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                orders -> onOrdersListener.onSuccess(orders, response.message)
                            }
                else ->
                    if (Constants.RESPONSE_CODE_NONE == response.code)
                        onOrdersListener.onSuccess(ArrayList(), response.message)
                    else
                        onOrdersListener.onNetworkError(response.message)

            }
        }, { onOrdersListener.onError() })
    }

    @SuppressLint("CheckResult")
    override fun getOrdersByStaff(onOrdersListener: OnOrdersListener) {
        GetOrdersRequest(context, Constants.TYPE_GET_ORDERS_STAFF).data.subscribe({ response ->
            when {
                response == null -> onOrdersListener.onError()
                Constants.RESPONSE_CODE_SUCCESSFUL == response.code ->
                    Single.just(GeneralUtil.sortingOrders(response.orderResponses))
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                orders -> onOrdersListener.onSuccess(orders, response.message)
                            }
                else ->
                    if (Constants.RESPONSE_CODE_NONE == response.code)
                        onOrdersListener.onSuccess(ArrayList(), response.message)
                    else
                        onOrdersListener.onNetworkError(response.message)

            }
        }, { onOrdersListener.onError() })
    }
}