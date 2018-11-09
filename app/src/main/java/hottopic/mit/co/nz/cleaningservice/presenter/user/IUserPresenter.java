package hottopic.mit.co.nz.cleaningservice.presenter.user;

import hottopic.mit.co.nz.cleaningservice.entities.network.LoginResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.RegisterResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.UserInfoEditResponse;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IUserPresenter {
    void doLogin(String username, String password);
    void doRegister(UserInfo userInfo);
    void userInfoEdit(UserInfo userInfo);

    void loginResult(LoginResponse response);
    void registerResult(RegisterResponse response);
    void userInfoEditResult(UserInfoEditResponse response);
}
