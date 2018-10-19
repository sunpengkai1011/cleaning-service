package hottopic.mit.co.nz.cleaningservice.view.login;


import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface ILoginView {
    void loginResult(UserInfo userInfo, int code);
}
