package hottopic.mit.co.nz.cleaningservice.view.user;


import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.network.ProductResponse;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IUserView {
    void registerResult(boolean result, String message);
    void loginResult(UserInfo userInfo, List<ProductResponse> products, String message);
    void userInfoEditResult(UserInfo userInfo, String message);
}
