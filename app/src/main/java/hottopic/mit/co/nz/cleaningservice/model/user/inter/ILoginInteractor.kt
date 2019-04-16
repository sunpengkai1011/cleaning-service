package hottopic.mit.co.nz.cleaningservice.model.user.inter

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.listener.OnLoginListener

interface ILoginInteractor {
    fun saveData(userInfo: UserInfo, serviceTypes: ServiceTypes)
    fun login(username: String?, password: String?, onLoginListener: OnLoginListener)
}