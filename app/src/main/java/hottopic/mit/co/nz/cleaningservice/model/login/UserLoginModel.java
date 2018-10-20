package hottopic.mit.co.nz.cleaningservice.model.login;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class UserLoginModel implements IUserLogin {
    private UserInfo userInfo;
    private Context context;

    public UserLoginModel(Context context, String username){
        this.context = context;
        userInfo = (UserInfo) GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, username), UserInfo.class);
    }

    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public boolean login(String username, String password) {
        if (username.equals(userInfo.getUserName()) && password.equals(userInfo.getPassword())){
            return true;
        }
        return false;
    }
}
