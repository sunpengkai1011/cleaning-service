package hottopic.mit.co.nz.cleaningservice.model.user;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IUser {
    void doLogin(String username, String password);
    void doRegister(UserInfo userInfo);
    void userInfoEdit(UserInfo userInfo);
}
