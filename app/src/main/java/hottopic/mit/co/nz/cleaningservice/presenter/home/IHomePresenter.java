package hottopic.mit.co.nz.cleaningservice.presenter.home;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IHomePresenter {
    void getServiceTypes();
    UserInfo getUserInfo(String username);
}
