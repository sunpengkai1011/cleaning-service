package hottopic.mit.co.nz.cleaningservice.model.order.inter

import hottopic.mit.co.nz.cleaningservice.entities.network.params.OrderBookingParams
import hottopic.mit.co.nz.cleaningservice.listener.OnBooleanRepListener

interface IOrderBookingInteractor {
    fun orderBooking(orderBookingParams: OrderBookingParams, onBooleanRepListener: OnBooleanRepListener)
}