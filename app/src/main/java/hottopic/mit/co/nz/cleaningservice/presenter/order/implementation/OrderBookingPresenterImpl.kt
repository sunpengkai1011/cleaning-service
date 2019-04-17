package hottopic.mit.co.nz.cleaningservice.presenter.order.implementation

import android.content.Context
import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams
import hottopic.mit.co.nz.cleaningservice.listener.OnBooleanRepListener
import hottopic.mit.co.nz.cleaningservice.model.order.implementation.OrderBookingInteractor
import hottopic.mit.co.nz.cleaningservice.presenter.order.inter.IOrderBookingPresenter
import hottopic.mit.co.nz.cleaningservice.view.order.inter.IOrderBookingView

class OrderBookingPresenterImpl(private val context: Context, private val iOrderBookingView: IOrderBookingView): IOrderBookingPresenter, OnBooleanRepListener{
    override fun orderBooking(orderBookingParams: OrderBookingParams) {
        val orderBookingInteractor = OrderBookingInteractor(context)
        orderBookingInteractor.orderBooking(orderBookingParams, this)
    }

    override fun onSuccess(result: Boolean, message: String) {
        iOrderBookingView.apply {
            showMessage(message)
            success()
        }
    }

    override fun onError() {
        iOrderBookingView.showMessage("Service booking failed")
    }

    override fun onNetworkError(message: String) {
        iOrderBookingView.showMessage(message)
    }
}