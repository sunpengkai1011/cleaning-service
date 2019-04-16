package hottopic.mit.co.nz.cleaningservice.listener

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo

interface OnUserEditListener : OnBaseListener {
    fun onSuccess(userInfo: UserInfo, message: String)
}