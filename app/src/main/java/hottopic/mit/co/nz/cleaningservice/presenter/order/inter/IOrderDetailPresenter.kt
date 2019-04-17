package hottopic.mit.co.nz.cleaningservice.presenter.order.inter

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order

interface IOrderDetailPresenter {
    fun orderStarted(order: Order)
    fun orderFinished(order: Order)
}