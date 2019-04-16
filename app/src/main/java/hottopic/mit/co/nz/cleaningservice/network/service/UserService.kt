package hottopic.mit.co.nz.cleaningservice.network.service

import hottopic.mit.co.nz.cleaningservice.entities.network.response.LoginResponse
import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse
import hottopic.mit.co.nz.cleaningservice.entities.network.response.UserInfoEditResponse
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * The request service is related with user information
 */
interface UserService {
    @POST("cleaning/user/{username}")
    fun signin(@Path("username") username: String?, @Header("Authorization") authorization: String): Single<LoginResponse?>?

    @POST("cleaning/user")
    fun register(@Body userInfo: UserInfo): Single<BooleanResponse?>?

    @POST("cleaning/edit")
    fun userEdit(@Body userInfo: UserInfo): Single<UserInfoEditResponse?>?
}
