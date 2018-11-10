package hottopic.mit.co.nz.cleaningservice.presenter.user;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.network.LoginResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.BooleanResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.UserInfoEditResponse;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.user.IUser;
import hottopic.mit.co.nz.cleaningservice.model.user.UserModel;
import hottopic.mit.co.nz.cleaningservice.presenter.order.OrderPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.view.user.IUserView;

public class UserPresenterImpl implements IUserPresenter {
    private IUserView iUserView;
    private IUser iUser;
    private Context context;

    public UserPresenterImpl(Context context, IUserView iUserView){
        this.iUserView = iUserView;
        this.context = context;
    }

    @Override
    public void doLogin(String username, String password) {
        iUser = new UserModel(context, iUserView);
        iUser.doLogin(username, password);
    }

    @Override
    public void doRegister(UserInfo userInfo) {
        iUser = new UserModel(context, iUserView);
        iUser.doRegister(userInfo);
    }

    @Override
    public void userInfoEdit(UserInfo userInfo) {
        iUser = new UserModel(context, iUserView);
        iUser.userInfoEdit(userInfo);
    }

    @Override
    public void loginResult(LoginResponse response) {
        if (response == null){
            iUserView.loginResult(null, null,"Login failed");
        }else{
            if (Constants.RESPONSE_CODE_SUCCESSFUL == response.getCode()) {
                iUserView.loginResult(response.getUserInfo().get(0), new OrderPresenterImpl(context).sortingTypeData(response.getServiceTypes()), response.getMessage());
            }else{
                iUserView.loginResult(null, null, response.getMessage());
            }
        }
    }

    @Override
    public void registerResult(BooleanResponse response) {
        if (response == null){
            iUserView.registerResult(false, "Register failed");
        }else{
            if (Constants.RESPONSE_CODE_SUCCESSFUL == response.getCode()) {
                iUserView.registerResult(response.getResult(), response.getMessage());
            }else{
                iUserView.registerResult(false, response.getMessage());
            }
        }
    }

    @Override
    public void userInfoEditResult(UserInfoEditResponse response) {
        if (response == null){
            iUserView.userInfoEditResult(null, "User information edits failed");
        }else{
            if (Constants.RESPONSE_CODE_SUCCESSFUL == response.getCode()) {
                iUserView.userInfoEditResult(response.getUserInfo().get(0), response.getMessage());
            }else{
                iUserView.userInfoEditResult(null, response.getMessage());
            }
        }
    }
}
