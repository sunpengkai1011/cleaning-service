package hottopic.mit.co.nz.cleaningservice.model.register;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IUserRegister {

    UserInfo getUserInfo();
    boolean register(UserInfo userInfo);
}
