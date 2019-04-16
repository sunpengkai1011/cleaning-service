package hottopic.mit.co.nz.cleaningservice.entities.network.response

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo

class UserInfoResponse : BaseResponse() {
    var userInfo: List<UserInfo>? = null
    var type: Int = 0
}
