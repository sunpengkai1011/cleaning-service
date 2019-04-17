package hottopic.mit.co.nz.cleaningservice.model.user.inter

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.listener.OnBooleanRepListener

interface IRegisterInteractor {
    fun register(userInfo: UserInfo, onRegisterListener: OnBooleanRepListener)
}