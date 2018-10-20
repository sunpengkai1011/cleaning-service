package hottopic.mit.co.nz.cleaningservice.model.login;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IUserLogin {

    UserInfo getUserInfo();

    boolean login(String username, String password);
}
