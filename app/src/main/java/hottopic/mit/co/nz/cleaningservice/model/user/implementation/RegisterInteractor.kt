package hottopic.mit.co.nz.cleaningservice.model.user.implementation

import android.annotation.SuppressLint
import android.content.Context
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.listener.OnBooleanRepListener
import hottopic.mit.co.nz.cleaningservice.model.user.inter.IRegisterInteractor
import hottopic.mit.co.nz.cleaningservice.network.RegisterRequest

class RegisterInteractor(private val context: Context): IRegisterInteractor {

    @SuppressLint("CheckResult")
    override fun register(userInfo: UserInfo, onRegisterListener: OnBooleanRepListener) {
        RegisterRequest(context, userInfo).data.subscribe ({
            response ->
            when {
                response == null -> onRegisterListener.onError()
                Constants.RESPONSE_CODE_SUCCESSFUL == response.code -> onRegisterListener.onSuccess(response.result, response.message)
                else -> onRegisterListener.onNetworkError(response.message)
            }
        }, { onRegisterListener.onError()})
    }
}