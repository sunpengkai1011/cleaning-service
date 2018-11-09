package hottopic.mit.co.nz.cleaningservice.entities.network;

import java.io.Serializable;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public class UserInfoEditResponse extends BaseResponse implements Serializable {
    private List<UserInfo> userInfo;

    public List<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }
}
