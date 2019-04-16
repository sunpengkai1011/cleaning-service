package hottopic.mit.co.nz.cleaningservice.view.user.inter

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo

interface ILoginView {
    fun showMessage(message: String)
    fun navigateToHome(userInfo: UserInfo, serviceTypes: ServiceTypes)
}