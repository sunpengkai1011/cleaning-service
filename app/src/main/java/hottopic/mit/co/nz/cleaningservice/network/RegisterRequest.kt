package hottopic.mit.co.nz.cleaningservice.network

import android.content.Context

import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import hottopic.mit.co.nz.cleaningservice.network.service.UserService
import io.reactivex.Single

/**
 * The request of registration.
 */
class RegisterRequest(context: Context, private val userInfo: UserInfo) : BaseRequest<UserService, BooleanResponse, BooleanResponse>(context) {

    override val header: Map<String, String>?
        get() = null

    override val endpointClass: Class<UserService>
        get() = UserService::class.java

    override fun getEndpoint(endpoint: UserService?): Single<BooleanResponse?>? {
        return endpoint?.register(userInfo)
    }

    override fun dealWithResponse(response: BooleanResponse): BooleanResponse? {
        return response
    }
}