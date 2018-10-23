package hottopic.mit.co.nz.cleaningservice.model.user;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IUserRegister {

    UserInfo getUserInfo(String username);
    boolean register(UserInfo userInfo);
}
