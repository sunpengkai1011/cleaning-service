package hottopic.mit.co.nz.cleaningservice.network

import android.content.Context

import hottopic.mit.co.nz.cleaningservice.entities.network.response.UserInfoEditResponse
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.network.service.UserService
import io.reactivex.Single

/**
 * The request of editing the user information.
 */
class UserInfoEditRequest(context: Context, private val userInfo: UserInfo) : BaseRequest<UserService, UserInfoEditResponse, UserInfoEditResponse>(context) {

    override val header: Map<String, String>?
        get() = null

    override val endpointClass: Class<UserService>
        get() = UserService::class.java

    override fun getEndpoint(endpoint: UserService?): Single<UserInfoEditResponse?>? {
        return endpoint?.userEdit(userInfo)
    }

    override fun dealWithResponse(response: UserInfoEditResponse): UserInfoEditResponse? {
        return response
    }
}
