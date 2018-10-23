package hottopic.mit.co.nz.cleaningservice.model.user;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IUserInfoEdit {
    UserInfo getUserInfo();
    boolean editUserInfo(UserInfo userInfo);
}
