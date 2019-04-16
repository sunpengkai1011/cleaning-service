package hottopic.mit.co.nz.cleaningservice.entities.orders

import java.io.Serializable

class Discount(var id: Int, var balance: Float, private var price: Float) : Serializable {

    fun formatPrice(): String {
        return "$ ${this.price.toInt()}"
    }

    fun formatBalance(): String {
        return "$ ${this.balance.toInt()}"
    }
}
