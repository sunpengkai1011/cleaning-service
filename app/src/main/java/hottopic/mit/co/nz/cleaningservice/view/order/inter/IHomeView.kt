package hottopic.mit.co.nz.cleaningservice.view.order.inter

import hottopic.mit.co.nz.cleaningservice.entities.orders.SubServiceType

interface IHomeView {
    fun navigateToBooking(serviceType: SubServiceType)
    fun navigateToOrder()
    fun goToAdverse()
}