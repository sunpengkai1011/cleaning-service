package hottopic.mit.co.nz.cleaningservice.entities.network.response

import java.io.Serializable

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo

class UserInfoEditResponse : BaseResponse(), Serializable {
    var userInfo: List<UserInfo>? = null
}
