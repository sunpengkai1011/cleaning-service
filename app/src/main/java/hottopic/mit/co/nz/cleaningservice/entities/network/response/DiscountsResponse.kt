package hottopic.mit.co.nz.cleaningservice.entities.network.response

import hottopic.mit.co.nz.cleaningservice.entities.orders.Discount

class DiscountsResponse : BaseResponse() {
    var discounts: List<Discount>? = null
}
