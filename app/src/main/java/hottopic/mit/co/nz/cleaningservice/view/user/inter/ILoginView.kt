package hottopic.mit.co.nz.cleaningservice.view.user.inter

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.view.IBaseView

interface ILoginView: IBaseView {
    fun navigateToHome(userInfo: UserInfo, serviceTypes: ServiceTypes)
}