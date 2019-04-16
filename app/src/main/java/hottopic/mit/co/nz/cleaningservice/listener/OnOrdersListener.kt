package hottopic.mit.co.nz.cleaningservice.listener

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order

interface OnOrdersListener: OnBaseListener {
    fun onSuccess(orders: List<Order>, message: String)
}