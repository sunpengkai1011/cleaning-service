package hottopic.mit.co.nz.cleaningservice.entities.network.response

import java.io.Serializable

class ProductResponse(var sub_id: Int, var main_id: Int, var sub_type_name: String?, var main_type_name: String?
                      , var product_name: String?, var unit_price: Float, var unit: String?
                      , var bulk_discount: Float, var icon: String?, var product_icon: String?) : Serializable {
    val id: Int = 0
}
