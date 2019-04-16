package hottopic.mit.co.nz.cleaningservice.view.user.inter

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo

interface IUserEditView {
    fun navigateToEdit()
    fun edit()
    fun showMessage(message: String)
    fun showUserInfo(userInfo: UserInfo)
    fun navigateToTopUp()
    fun editAvalable()
}
