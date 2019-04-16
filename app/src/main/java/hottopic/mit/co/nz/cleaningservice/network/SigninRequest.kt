package hottopic.mit.co.nz.cleaningservice.network

import android.content.Context
import android.util.Base64

import java.io.UnsupportedEncodingException

import hottopic.mit.co.nz.cleaningservice.entities.network.response.LoginResponse
import hottopic.mit.co.nz.cleaningservice.network.service.UserService
import io.reactivex.Single


/**
 * The request of sign in.
 */
class SigninRequest(context: Context, private val userName: String?, private val password: String?) : BaseRequest<UserService, LoginResponse, LoginResponse>(context) {

    override val endpointClass: Class<UserService>
        get() = UserService::class.java

    override val header: Map<String, String>?
        get() = null

    override fun getEndpoint(endpoint: UserService?): Single<LoginResponse?>? {
        val str = "$userName:$password"
        var authorization = ""
        try {
            val data = str.toByteArray(charset("UTF-8"))
            authorization = Base64.encodeToString(data, Base64.NO_WRAP)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return endpoint?.signin(userName, authorization)
    }

    override fun dealWithResponse(response: LoginResponse): LoginResponse {
        return response
    }
}
