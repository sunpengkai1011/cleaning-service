package hottopic.mit.co.nz.cleaningservice.presenter.user.implementation

import android.content.Context
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.listener.OnBooleanRepListener
import hottopic.mit.co.nz.cleaningservice.model.user.implementation.RegisterInteractor
import hottopic.mit.co.nz.cleaningservice.presenter.user.inter.IRegisterPresenter
import hottopic.mit.co.nz.cleaningservice.view.user.inter.IRegisterView

class RegisterPresenterImpl(private val context: Context, private val iRegisterView: IRegisterView): IRegisterPresenter, OnBooleanRepListener {
    override fun onSuccess(result: Boolean, message: String) {
        iRegisterView.apply {
            showMessage(message)
            returnLogin()
        }
    }

    override fun onError() {
        iRegisterView.showMessage("Register failed")
    }

    override fun onNetworkError(message: String) {
        iRegisterView.showMessage(message)
    }

    override fun register(userInfo: UserInfo) {
        val registerInteractor = RegisterInteractor(context)
        registerInteractor.register(userInfo, this)
    }
}