package hottopic.mit.co.nz.cleaningservice.model.register;

import android.content.Context;

import com.google.gson.Gson;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class UserRegisterModel implements IUserRegister {
    private UserInfo userInfo;
    private Context context;

    public UserRegisterModel(Context context, UserInfo userInfo){
        this.userInfo = userInfo;
        this.context = context;
    }

    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public boolean register(UserInfo userInfo) {
        if (fakeRegisterRequest(userInfo)){
            return true;
        }
        return false;
    }

    private boolean fakeRegisterRequest(UserInfo userInfo){
        if (userInfo != null){
            GeneralUtil.storDataBySP(context, userInfo.getUserName(), GeneralUtil.toJson(userInfo, UserInfo.class));
            return true;
        }
        return false;
    }
}
