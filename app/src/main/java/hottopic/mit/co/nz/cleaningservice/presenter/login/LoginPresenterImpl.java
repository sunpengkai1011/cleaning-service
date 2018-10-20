package hottopic.mit.co.nz.cleaningservice.presenter.login;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.login.IUserLogin;
import hottopic.mit.co.nz.cleaningservice.model.login.UserLoginModel;
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
        iUserLogin = new UserLoginModel(context, username);
        if (iUserLogin.login(username, password)){
            userInfo = new UserInfo();
            userInfo.setUserName(username);
            iLoginView.loginResult(userInfo, Constants.RESPONSE_CODE_SUCCESSFUL);
        }else {
            iLoginView.loginResult(new UserInfo(), Constants.RESPONSE_CODE_FAIL);
        }
    }
}
