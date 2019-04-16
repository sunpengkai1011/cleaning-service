package hottopic.mit.co.nz.cleaningservice.presenter.order.inter

import hottopic.mit.co.nz.cleaningservice.listener.OnOrdersListener

interface IOrdersPresenter {
    fun getOrdersByCustomer(userId: Int)
    fun getOrdersByStaff()
}