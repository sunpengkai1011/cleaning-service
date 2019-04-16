package hottopic.mit.co.nz.cleaningservice.model.order.inter

import hottopic.mit.co.nz.cleaningservice.listener.OnOrdersListener

interface IOrdersInteractor {
    fun getOrdersByCustomer(userId: Int, onOrdersListener: OnOrdersListener)
    fun getOrdersByStaff(onOrdersListener: OnOrdersListener)
}