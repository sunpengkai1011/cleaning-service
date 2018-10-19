package hottopic.mit.co.nz.cleaningservice.presenter.login;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.login.IUserLogin;
import hottopic.mit.co.nz.cleaningservice.model.login.UserLoginModel;
import hottopic.mit.co.nz.cleaningservice.view.login.ILoginView;

public class LoginPresenterCompl implements ILoginPresenter {
    private ILoginView iLoginView;
    private IUserLogin iUserLogin;
    private UserInfo userInfo;

    public LoginPresenterCompl(ILoginView iLoginView){
        this.iLoginView = iLoginView;
        initUserLogin();
    }

    @Override
    public void doLogin(String username, String password) {
        if (iUserLogin.checkLoginVisible(username, password)){
            userInfo = new UserInfo();
            userInfo.setUserName(username);
            iLoginView.loginResult(userInfo, Constants.RESPONSE_CODE_SUCCESSFUL);
        }else {
            iLoginView.loginResult(new UserInfo(), Constants.RESPONSE_CODE_FAIL);
        }
    }

    private void initUserLogin(){
        iUserLogin = new UserLoginModel("kevin", "123456");
    }
}
