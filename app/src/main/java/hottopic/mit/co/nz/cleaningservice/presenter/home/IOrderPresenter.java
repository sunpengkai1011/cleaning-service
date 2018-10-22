package hottopic.mit.co.nz.cleaningservice.presenter.home;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public interface IOrderPresenter {
    void getOrder(UserInfo userInfo);
    void startedOrder(int position, String started);
    void finishedOrder(int position, String finished);
}
