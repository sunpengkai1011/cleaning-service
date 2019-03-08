package hottopic.mit.co.nz.cleaningservice.presenter.home;

import android.content.Context;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.network.LoginResponse;
import hottopic.mit.co.nz.cleaningservice.entities.network.ServiceTypesResponse;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.order.IOrder;
import hottopic.mit.co.nz.cleaningservice.model.order.IService;
import hottopic.mit.co.nz.cleaningservice.model.order.OrderModel;
import hottopic.mit.co.nz.cleaningservice.model.order.ServiceModel;
import hottopic.mit.co.nz.cleaningservice.model.user.IUser;
import hottopic.mit.co.nz.cleaningservice.model.user.UserModel;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.view.home.IHomeView;

public class HomePresenterImpl implements IHomePresenter {
    private Context context;
    private IHomeView iHomeView;
    private IOrder iOrder;

    public HomePresenterImpl(Context context, IHomeView iHomeView) {
        this.iHomeView = iHomeView;
        this.context = context;
    }

    @Override
    public void getServiceTypes() {
        iOrder = new OrderModel(context, iHomeView);
        iOrder.getServiceTypes();
    }

    @Override
    public void serviceTypesResult(ServiceTypesResponse response) {
        if (response == null){
            iHomeView.getSerivceError(response.getMessage());
        }else{
            if (Constants.RESPONSE_CODE_SUCCESSFUL == response.getCode()){
                iHomeView.getServiceTypes(GeneralUtil.sortingTypeData(response.getServiceTypes()));
            }else{
                iHomeView.getSerivceError(response.getMessage());
            }
        }
    }
}
