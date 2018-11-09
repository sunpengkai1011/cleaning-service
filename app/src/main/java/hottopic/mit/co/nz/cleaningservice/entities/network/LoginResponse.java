package hottopic.mit.co.nz.cleaningservice.entities.network;

import java.io.Serializable;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;


/**
 * The login response entity.
 */
public class LoginResponse extends BaseResponse implements Serializable{
    private List<UserInfo> userInfo;
    List<ProductResponse> serviceTypes;

    public List<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }

    public List<ProductResponse> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ProductResponse> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }
}
