package hottopic.mit.co.nz.cleaningservice.view.order.inter

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order

interface IOrdersView {
    fun navigateToDetail(order: Order)
    fun navigateToUserInfo()
    fun showOrders(orders: List<Order>)
    fun showMessage(message: String)
}