package hottopic.mit.co.nz.cleaningservice.model.user;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class UserLoginModel implements IUserLogin {
    private UserInfo userInfo;
    private Context context;

    public UserLoginModel(Context context){
        this.context = context;
    }

    @Override
    public UserInfo getUserInfo(String username) {
        userInfo = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, username), UserInfo.class);
        return userInfo;
    }

    @Override
    public boolean login(String username, String password) {
        userInfo =  GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, username), UserInfo.class);
        if (userInfo != null) {
            if (username.equals(userInfo.getUserName()) && password.equals(userInfo.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
