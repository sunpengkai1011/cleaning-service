package hottopic.mit.co.nz.cleaningservice.model.user;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.network.RegisterRequest;
import hottopic.mit.co.nz.cleaningservice.network.SigninRequest;
import hottopic.mit.co.nz.cleaningservice.network.UserInfoEditRequest;
import hottopic.mit.co.nz.cleaningservice.presenter.user.IUserPresenter;
import hottopic.mit.co.nz.cleaningservice.presenter.user.UserPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.view.user.IUserView;

public class UserModel implements IUser {
    private Context context;
    private IUserPresenter presenter;

    public UserModel(Context context, IUserView iUserView) {
        this.context = context;
        presenter = new UserPresenterImpl(context, iUserView);
    }

    @Override
    public void doLogin(String username, String password) {
        new SigninRequest(context, username, password).getData().subscribe(
                response -> presenter.loginResult(response), e -> presenter.loginResult(null));
    }

    @Override
    public void doRegister(UserInfo userInfo) {
        new RegisterRequest(context, userInfo).getData().subscribe(
                registerResponse -> presenter.registerResult(registerResponse), e -> presenter.registerResult(null));
    }

    @Override
    public void userInfoEdit(UserInfo userInfo) {
        new UserInfoEditRequest(context, userInfo).getData().subscribe(
                userInfoEditResponse -> presenter.userInfoEditResult(userInfoEditResponse),
                e -> presenter.userInfoEditResult(null));
    }
}
