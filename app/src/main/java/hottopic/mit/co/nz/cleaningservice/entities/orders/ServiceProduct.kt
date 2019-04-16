package hottopic.mit.co.nz.cleaningservice.entities.orders

import java.io.Serializable

data class ServiceProduct(var id: Int, var main_id: Int, var sub_id: Int, var product_name: String?
                     , var icon: String?, var unit_price: Float, var unit: String?, var quantity: Int) : Serializable {

    fun formatPrice(): String {
        return "$ ${this.unit_price} ${this.unit}"
    }
}
