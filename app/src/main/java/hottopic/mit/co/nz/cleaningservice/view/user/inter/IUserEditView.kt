package hottopic.mit.co.nz.cleaningservice.view.user.inter

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.view.IBaseView

interface IUserEditView: IBaseView {
    fun navigateToEdit()
    fun edit()
    fun showUserInfo(userInfo: UserInfo)
    fun navigateToTopUp()
    fun editAvalable()
}
