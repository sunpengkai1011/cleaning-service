package hottopic.mit.co.nz.cleaningservice.network.service;

import hottopic.mit.co.nz.cleaningservice.entities.network.response.LoginResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.UserInfoEditResponse;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * The request service is related with user information
 */
public interface UserService {
    @POST("cleaning/user/{username}")
    Single<LoginResponse> signin(@Path("username") String username, @Header("Authorization") String authorization);

    @POST("cleaning/user")
    Single<BooleanResponse> register(@Body UserInfo userInfo);

    @POST("cleaning/edit")
    Single<UserInfoEditResponse> userEdit(@Body UserInfo userInfo);
}
