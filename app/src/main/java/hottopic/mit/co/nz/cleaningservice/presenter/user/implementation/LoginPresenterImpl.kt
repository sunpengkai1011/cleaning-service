package hottopic.mit.co.nz.cleaningservice.presenter.user.implementation

import android.content.Context
import hottopic.mit.co.nz.cleaningservice.R
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.listener.OnLoginListener
import hottopic.mit.co.nz.cleaningservice.model.user.implementation.LoginInteractor
import hottopic.mit.co.nz.cleaningservice.presenter.user.inter.ILoginPresenter
import hottopic.mit.co.nz.cleaningservice.view.user.inter.ILoginView

class LoginPresenterImpl(private val context: Context, private val iLoginView: ILoginView) : ILoginPresenter, OnLoginListener {

    override fun onUsernameError() {
        iLoginView.showMessage(context.resources.getString(R.string.toast_empty_username))
    }

    override fun onPasswordError() {
        iLoginView.showMessage(context.resources.getString(R.string.toast_empty_password))
    }

    override fun onSuccess(userInfo: UserInfo, serviceTypes: ServiceTypes, message: String) {
        iLoginView.apply {
            showMessage(message)
            navigateToHome(userInfo, serviceTypes)
        }
    }

    override fun onError() {
        iLoginView.showMessage("login failed!")
    }

    override fun onNetworkError(message: String) {
        iLoginView.showMessage(message)
    }

    override fun login(username: String, password: String) {
        val iLoginInteractor = LoginInteractor(context)
        iLoginInteractor.login(username, password, this)
    }

}