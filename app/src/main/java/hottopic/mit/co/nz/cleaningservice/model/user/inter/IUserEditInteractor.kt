package hottopic.mit.co.nz.cleaningservice.model.user.inter

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.listener.OnUserEditListener

interface IUserEditInteractor {
    fun userInfoEdit(userInfo: UserInfo, onUserEditListener: OnUserEditListener)
}