package hottopic.mit.co.nz.cleaningservice.entities.network.params

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceProduct

class OrderBookingParams {
    var order: Order? = null
    var products: List<ServiceProduct>? = null
}
