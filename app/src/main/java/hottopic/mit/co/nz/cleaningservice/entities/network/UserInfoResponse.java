package hottopic.mit.co.nz.cleaningservice.entities.network;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public class UserInfoResponse extends BaseResponse {
    private List<UserInfo> userInfo;
    private int type;

    public List<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
