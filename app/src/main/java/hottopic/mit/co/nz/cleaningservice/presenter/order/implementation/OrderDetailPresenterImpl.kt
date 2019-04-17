package hottopic.mit.co.nz.cleaningservice.presenter.order.implementation

import android.content.Context
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import hottopic.mit.co.nz.cleaningservice.listener.OnBooleanRepListener
import hottopic.mit.co.nz.cleaningservice.model.order.implementation.OrderDetailInteractor
import hottopic.mit.co.nz.cleaningservice.presenter.order.inter.IOrderDetailPresenter
import hottopic.mit.co.nz.cleaningservice.view.order.inter.IOrderDetailView

class OrderDetailPresenterImpl(private val context: Context, private val iOrderDetailView: IOrderDetailView): IOrderDetailPresenter, OnBooleanRepListener{
    override fun orderStarted(order: Order) {
        val orderDetailInteractor = OrderDetailInteractor(context)
        orderDetailInteractor.orderChangeStatus(order, Constants.TYPE_SERVICE_STARTED, this)
    }

    override fun orderFinished(order: Order) {
        val orderDetailInteractor = OrderDetailInteractor(context)
        orderDetailInteractor.orderChangeStatus(order, Constants.TYPE_SERVICE_FINISHED, this)
    }

    override fun onSuccess(result: Boolean, message: String) {
        iOrderDetailView.apply {
            showMessage(message)
            success()
        }
    }

    override fun onError() {
        iOrderDetailView.showMessage("Request failed")
    }

    override fun onNetworkError(message: String) {
        iOrderDetailView.showMessage(message)
    }
}