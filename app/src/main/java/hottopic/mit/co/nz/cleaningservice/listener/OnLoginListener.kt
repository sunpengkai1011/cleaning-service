package hottopic.mit.co.nz.cleaningservice.listener

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo

interface OnLoginListener: OnBaseListener {
    fun onUsernameError()
    fun onPasswordError()
    fun onSuccess(userInfo: UserInfo, serviceTypes: ServiceTypes, message: String)
}