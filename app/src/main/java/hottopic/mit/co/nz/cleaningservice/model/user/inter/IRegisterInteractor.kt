package hottopic.mit.co.nz.cleaningservice.model.user.inter

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.listener.OnRegisterListener

interface IRegisterInteractor {
    fun register(userInfo: UserInfo, onRegisterListener: OnRegisterListener)
}