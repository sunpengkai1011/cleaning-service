package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;

import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.entities.network.RegisterResponse;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.network.service.UserService;
import io.reactivex.Single;

/**
 * The request of registration.
 */
public class RegisterRequest extends BaseRequest<UserService, RegisterResponse, RegisterResponse> {
    private UserInfo userInfo;

    public RegisterRequest(Context context, UserInfo userInfo) {
        super(context);
        this.userInfo = userInfo;
    }

    @Override
    protected Map<String, String> getHeader() {
        return null;
    }

    @Override
    protected Class getEndpointClass() {
        return UserService.class;
    }

    @Override
    protected Single<RegisterResponse> getEndpoint(UserService endpoint) {
        return endpoint.register(userInfo);
    }

    @Override
    protected RegisterResponse dealWithResponse(RegisterResponse response) {
        return response;
    }
}