package hottopic.mit.co.nz.cleaningservice.model.user;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class UserInfoEditModel implements IUserInfoEdit {
    private Context context;
    private UserInfo userInfo;

    public UserInfoEditModel(Context context) {
        this.context = context;
    }

    @Override
    public UserInfo getUserInfo() {
        userInfo = GeneralUtil.fromJson(GeneralUtil.getDataFromSP(context, Constants.SP_KEY_USERINFO), UserInfo.class);
        return userInfo;
    }

    @Override
    public boolean editUserInfo(UserInfo userInfo) {
        if (userInfo != null){
            GeneralUtil.storDataBySP(context, Constants.SP_KEY_USERINFO, GeneralUtil.toJson(userInfo, UserInfo.class));
            return true;
        }
        return false;
    }
}
