package hottopic.mit.co.nz.cleaningservice.entities.orders

import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil
import java.io.Serializable

data class SubServiceType(var id: Int, var main_id: Int, var type_name: String?, var bulk_discount: Float, var icon: String?, var products: MutableList<ServiceProduct>?) : Serializable {

    fun formatBulkDiscount(): String {
        return GeneralUtil.formatDiscount(this.bulk_discount)
    }
}
