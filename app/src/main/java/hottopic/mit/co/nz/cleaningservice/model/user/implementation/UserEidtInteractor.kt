package hottopic.mit.co.nz.cleaningservice.model.user.implementation

import android.annotation.SuppressLint
import android.content.Context
import hottopic.mit.co.nz.cleaningservice.Constants
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.listener.OnUserEditListener
import hottopic.mit.co.nz.cleaningservice.model.user.inter.IUserEditInteractor
import hottopic.mit.co.nz.cleaningservice.network.UserInfoEditRequest
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil

class UserEidtInteractor(private val context: Context) : IUserEditInteractor{
    @SuppressLint("CheckResult")
    override fun userInfoEdit(userInfo: UserInfo, onUserEditListener: OnUserEditListener) {
        UserInfoEditRequest(context, userInfo).data.subscribe ({
            response ->
            when{
                response == null -> onUserEditListener.onError()
                Constants.RESPONSE_CODE_SUCCESSFUL == response.code -> {
                    val data = response.userInfo?.get(0)
                    if (data != null) {
                        GeneralUtil.storDataBySP(context, Constants.SP_KEY_USERINFO, GeneralUtil.toJson(data, UserInfo::class.java))
                        Constants.userInfo = data
                        onUserEditListener.onSuccess(data, response.message)
                    }
                }
                else -> onUserEditListener.onNetworkError(response.message)
            }
        }, { onUserEditListener.onError() })
    }
}