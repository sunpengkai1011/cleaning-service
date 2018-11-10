package hottopic.mit.co.nz.cleaningservice.view.user;


import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IUserView {
    void registerResult(boolean result, String message);
    void loginResult(UserInfo userInfo, ServiceTypes serviceTypes, String message);
    void userInfoEditResult(UserInfo userInfo, String message);
}
