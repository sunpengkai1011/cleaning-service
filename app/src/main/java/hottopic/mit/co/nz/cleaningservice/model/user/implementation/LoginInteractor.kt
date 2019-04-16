package hottopic.mit.co.nz.cleaningservice.model.user.implementation

import android.content.Context
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.listener.OnLoginListener
import hottopic.mit.co.nz.cleaningservice.model.user.inter.ILoginInteractor
import hottopic.mit.co.nz.cleaningservice.network.SigninRequest
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginInteractor(private val context: Context) : ILoginInteractor {

    override fun saveData(userInfo: UserInfo, serviceTypes: ServiceTypes) {
        GeneralUtil.storDataBySP(context, Constants.SP_KEY_USERINFO, GeneralUtil.toJson(userInfo, UserInfo::class.java))
        GeneralUtil.storDataBySP(context, Constants.SP_KEY_SERVICE_TYPE, GeneralUtil.toJson(serviceTypes, ServiceTypes::class.java))
        GeneralUtil.storeIntIntoSP(context, Constants.SP_KEY_LAST_LOGIN_TIMESTAMP, (System.currentTimeMillis() / 1000).toInt())
    }

    override fun login(username: String?, password: String?, onLoginListener: OnLoginListener) {
        when{
            username.isNullOrEmpty() -> onLoginListener.onUsernameError()
            password.isNullOrEmpty() -> onLoginListener.onPasswordError()
            else -> SigninRequest(context, username, password).data.subscribe ({
                response ->
                if (response == null) {
                    onLoginListener.onError()
                }else{
                    if (Constants.RESPONSE_CODE_SUCCESSFUL == response.code){
                        Single.just(GeneralUtil.sortingTypeData(response.serviceTypes))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { serviceTypes ->
                                    val userInfo = response.userInfo?.get(0)
                                    if (userInfo != null && serviceTypes != null) {
                                        Constants.userInfo = userInfo
                                        onLoginListener.onSuccess(userInfo, serviceTypes, response.message)
                                    } else
                                        onLoginListener.onError()
                                }
                    }else{
                        onLoginListener.onNetworkError(response.message)
                    }
                }
            }, { onLoginListener.onError() })
        }
    }
}