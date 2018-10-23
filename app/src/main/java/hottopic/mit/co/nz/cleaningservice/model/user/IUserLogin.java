package hottopic.mit.co.nz.cleaningservice.model.user;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IUserLogin {

    UserInfo getUserInfo(String username);

    boolean login(String username, String password);
}
