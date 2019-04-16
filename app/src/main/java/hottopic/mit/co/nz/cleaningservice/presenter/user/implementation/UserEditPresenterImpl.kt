package hottopic.mit.co.nz.cleaningservice.presenter.user.implementation

import android.content.Context
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.listener.OnUserEditListener
import hottopic.mit.co.nz.cleaningservice.model.user.implementation.UserEidtInteractor
import hottopic.mit.co.nz.cleaningservice.presenter.user.inter.IUserEditPresenter
import hottopic.mit.co.nz.cleaningservice.view.user.inter.IUserEditView

class UserEditPresenterImpl(private val context: Context, private val iUserEidtView: IUserEditView) : IUserEditPresenter, OnUserEditListener {

    override fun editUserInfo(userInfo: UserInfo) {
        val userEditInteractor = UserEidtInteractor(context)
        userEditInteractor.userInfoEdit(userInfo, this)
    }

    override fun onSuccess(userInfo: UserInfo, message: String) {
        iUserEidtView.apply {
            showMessage(message)
            showUserInfo(userInfo)
            editAvalable()
        }
    }

    override fun onError() {
        iUserEidtView.showMessage("User information edits failed")
    }

    override fun onNetworkError(message: String) {
        iUserEidtView.showMessage(message)
    }
}