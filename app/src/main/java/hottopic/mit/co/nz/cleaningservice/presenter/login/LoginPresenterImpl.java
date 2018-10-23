package hottopic.mit.co.nz.cleaningservice.presenter.login;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.user.IUserLogin;
import hottopic.mit.co.nz.cleaningservice.model.user.UserLoginModel;
import hottopic.mit.co.nz.cleaningservice.view.login.ILoginView;

public class LoginPresenterImpl implements ILoginPresenter {
    private ILoginView iLoginView;
    private IUserLogin iUserLogin;
    private UserInfo userInfo;
    private Context context;

    public LoginPresenterImpl(Context context, ILoginView iLoginView){
        this.iLoginView = iLoginView;
        this.context = context;
    }

    @Override
    public void doLogin(String username, String password) {
        iUserLogin = new UserLoginModel(context);
        if (iUserLogin.login(username, password)){
            userInfo = iUserLogin.getUserInfo(username);
            iLoginView.loginResult(userInfo, Constants.RESPONSE_CODE_SUCCESSFUL);
        }else {
            iLoginView.loginResult(new UserInfo(), Constants.RESPONSE_CODE_FAIL);
        }
    }
}
