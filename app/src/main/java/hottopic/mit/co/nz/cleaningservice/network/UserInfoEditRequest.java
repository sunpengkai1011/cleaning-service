package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.entities.network.response.UserInfoEditResponse;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.network.service.UserService;
import io.reactivex.Single;

/**
 * The request of editing the user information.
 */
public class UserInfoEditRequest extends BaseRequest<UserService, UserInfoEditResponse, UserInfoEditResponse> {
    private UserInfo userInfo;

    public UserInfoEditRequest(Context context, UserInfo userInfo) {
        super(context);
        this.userInfo = userInfo;
    }

    @Override
    protected Map<String, String> getHeader() {
        return null;
    }

    @Override
    protected Class<UserService> getEndpointClass() {
        return UserService.class;
    }

    @Override
    protected Single<UserInfoEditResponse> getEndpoint(UserService endpoint) {
        return endpoint.userEdit(userInfo);
    }

    @Override
    protected UserInfoEditResponse dealWithResponse(UserInfoEditResponse response) {
        return response;
    }
}
