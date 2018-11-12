package hottopic.mit.co.nz.cleaningservice.network;

import android.content.Context;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.entities.network.response.LoginResponse;
import hottopic.mit.co.nz.cleaningservice.network.service.UserService;
import io.reactivex.Single;


/**
 * The request of sign in.
 */
public class SigninRequest extends BaseRequest<UserService, LoginResponse, LoginResponse> {
    private String userName, password;

    public SigninRequest(Context context, String userName, String password) {
        super(context);
        this.userName = userName;
        this.password = password;
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
    protected Single<LoginResponse> getEndpoint(UserService endpoint) {

        String str = userName + ":" + password;
        String authorization = "";
        try {
            byte[] data = str.getBytes("UTF-8");
            authorization = Base64.encodeToString(data, Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return endpoint.signin(userName, authorization);
    }

    @Override
    protected LoginResponse dealWithResponse(LoginResponse response) {
        return response;
    }
}
