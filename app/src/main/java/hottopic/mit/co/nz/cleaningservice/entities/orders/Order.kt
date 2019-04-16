package hottopic.mit.co.nz.cleaningservice.entities.orders

import java.io.Serializable

import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil

data class Order(var id: Int, var user_id: Int, var date: String?, var subServiceType: SubServiceType?, var phone: String?
            , var city: String?, var suburb: String?, var street: String?, var status: Int, var amount: Float, var duration: Int
            , var start_time: String?, var end_time: String?, var quantity: Int, var feedback: String?, var rating: Int) : Serializable {

    val address: String
        get() = "$street, $suburb, $city"

    fun formatDuration(): String {
        return if (this.duration > 1) {
            this.duration.toString() + " Hours"
        } else {
            this.duration.toString() + " Hour"
        }
    }

    fun formatAmount(): String {
        return "$ ${GeneralUtil.priceFormat(this.amount)}"
    }

    fun formatQuantity(): String {
        return if (Constants.ID_SERVICE_BUFFERING == subServiceType?.id ||
                Constants.ID_SERVICE_CARPETWASH == subServiceType?.id ||
                Constants.ID_SERVICE_WATERBLASTING == subServiceType?.id) {
            "${this.quantity} square meters"
        } else {
            "${this.quantity} square meter"
        }
    }
}
