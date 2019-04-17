package hottopic.mit.co.nz.cleaningservice.view.order.inter

import hottopic.mit.co.nz.cleaningservice.view.IBaseView

interface IOrderBookingView: IBaseView {
    fun bookingService()
    fun success()
}