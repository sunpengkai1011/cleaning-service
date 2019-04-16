package hottopic.mit.co.nz.cleaningservice.presenter.order.implementation

import android.content.Context
import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import hottopic.mit.co.nz.cleaningservice.listener.OnOrdersListener
import hottopic.mit.co.nz.cleaningservice.model.order.implementation.OrdersInteractor
import hottopic.mit.co.nz.cleaningservice.presenter.order.inter.IOrdersPresenter
import hottopic.mit.co.nz.cleaningservice.view.order.inter.IOrdersView

class OrdersPresenterImpl(private val context: Context, private val iOrdersView: IOrdersView): IOrdersPresenter, OnOrdersListener {
    override fun getOrdersByCustomer(userId: Int) {
        val ordersInteractor = OrdersInteractor(context)
        ordersInteractor.getOrdersByCustomer(userId, this)
    }

    override fun getOrdersByStaff() {
        val ordersInteractor = OrdersInteractor(context)
        ordersInteractor.getOrdersByStaff(this)
    }

    override fun onSuccess(orders: List<Order>, message: String) {
        iOrdersView.apply{
            showMessage(message)
            showOrders(orders)
        }
    }

    override fun onError() {
        iOrdersView.showMessage("Get Orders Failed")
    }

    override fun onNetworkError(message: String) {
        iOrdersView.showMessage(message)
    }

}