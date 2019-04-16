package hottopic.mit.co.nz.cleaningservice.entities.users

import java.io.Serializable

data class UserInfo(var username: String?, var password: String?, var phone: String?,
               var email: String?, var city: String?, var suburb: String?, var street: String?,
               var role_id: Int, var balance: Float) : Serializable {
    var id: Int = 0
    var role_name: String? = null

    val address: String
        get() = "$street, $suburb, $city"

    fun formatBalance(): String {
        return "${this.balance}"
    }
}
