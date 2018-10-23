package hottopic.mit.co.nz.cleaningservice.model.user;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class UserRegisterModel implements IUserRegister {
    private Context context;

    public UserRegisterModel(Context context){
        this.context = context;
    }

    @Override
    public UserInfo getUserInfo(String username) {
        return GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, username), UserInfo.class);
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
