package hottopic.mit.co.nz.cleaningservice.view.order.inter

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import hottopic.mit.co.nz.cleaningservice.view.IBaseView

interface IOrdersView: IBaseView {
    fun navigateToDetail(order: Order)
    fun navigateToUserInfo()
    fun showOrders(orders: List<Order>)
}