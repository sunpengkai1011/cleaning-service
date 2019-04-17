package hottopic.mit.co.nz.cleaningservice.model.order.inter

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import hottopic.mit.co.nz.cleaningservice.listener.OnBooleanRepListener

interface IOrderDetailInteractor {
    fun orderChangeStatus(order: Order, code: Int, onBooleanRepListener: OnBooleanRepListener)
}