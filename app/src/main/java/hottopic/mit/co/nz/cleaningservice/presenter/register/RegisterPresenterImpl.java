package hottopic.mit.co.nz.cleaningservice.presenter.register;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.user.IUserRegister;
import hottopic.mit.co.nz.cleaningservice.model.user.UserRegisterModel;
import hottopic.mit.co.nz.cleaningservice.view.register.IRegisterView;

public class RegisterPresenterImpl implements IRegisterPresenter {
    private IRegisterView iRegisterView;
    private IUserRegister iUserRegister;
    private Context context;

    public RegisterPresenterImpl(Context context, IRegisterView iRegisterView){
        this.context = context;
        this.iRegisterView = iRegisterView;
    }

    @Override
    public void doRegister(UserInfo userInfo) {
        if (userInfo != null) {
            iUserRegister = new UserRegisterModel(context);
            if (iUserRegister.register(userInfo)) {
                iRegisterView.registerResult(iUserRegister.getUserInfo(userInfo.getUserName()), Constants.RESPONSE_CODE_SUCCESSFUL);
            } else {
                iRegisterView.registerResult(userInfo, Constants.RESPONSE_CODE_FAIL);
            }
        }else{
            iRegisterView.registerResult(new UserInfo(), Constants.RESPONSE_CODE_FAIL);
        }
    }
}
