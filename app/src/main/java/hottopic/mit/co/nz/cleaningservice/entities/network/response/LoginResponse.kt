package hottopic.mit.co.nz.cleaningservice.entities.network.response

import java.io.Serializable

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo


/**
 * The login response entity.
 */
class LoginResponse : BaseResponse(), Serializable {
    var userInfo: List<UserInfo?>? = null
    var serviceTypes: List<ProductResponse>? = null
}
