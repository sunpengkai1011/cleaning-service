package hottopic.mit.co.nz.cleaningservice.view.register;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IRegisterView {
    void registerResult(UserInfo userInfo, int code);
}
