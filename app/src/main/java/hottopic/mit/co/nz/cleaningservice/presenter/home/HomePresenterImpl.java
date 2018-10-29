package hottopic.mit.co.nz.cleaningservice.presenter.home;

import android.content.Context;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.order.IService;
import hottopic.mit.co.nz.cleaningservice.model.order.ServiceModel;
import hottopic.mit.co.nz.cleaningservice.model.user.IUserLogin;
import hottopic.mit.co.nz.cleaningservice.model.user.UserLoginModel;
import hottopic.mit.co.nz.cleaningservice.view.home.IHomeView;
import hottopic.mit.co.nz.cleaningservice.view.login.ILoginView;

public class HomePresenterImpl implements IHomePresenter {
    private Context context;
    private IHomeView iHomeView;
    private IService iService;
    private IUserLogin iUserLogin;

    public HomePresenterImpl(Context context) {
        this.context = context;
    }

    public HomePresenterImpl(Context context, IHomeView iHomeView) {
        this.iHomeView = iHomeView;
        iService = new ServiceModel(context);
        this.context = context;
    }

    @Override
    public void getServiceTypes() {
        List<ServiceType> cleaningTypes = iService.getCleaningTypes();
        List<ServiceType> ironingTypes = iService.getIroningTypes();
        if (cleaningTypes != null && ironingTypes != null){
            iHomeView.getServiceTypes(cleaningTypes, ironingTypes);
        }
    }

    @Override
    public UserInfo getUserInfo(String username) {
        iUserLogin = new UserLoginModel(context);
        return iUserLogin.getUserInfo(username);
    }
}
