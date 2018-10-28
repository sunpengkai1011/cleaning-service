package hottopic.mit.co.nz.cleaningservice.presenter.home;

import android.content.Context;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.model.order.IService;
import hottopic.mit.co.nz.cleaningservice.model.order.ServiceModel;
import hottopic.mit.co.nz.cleaningservice.view.home.IHomeView;

public class HomePresenterImpl implements IHomePresenter {
    private IHomeView iHomeView;
    private IService iService;

    public HomePresenterImpl(Context context, IHomeView iHomeView) {
        this.iHomeView = iHomeView;
        iService = new ServiceModel(context);
    }

    @Override
    public void getServiceTypes() {
        List<ServiceType> cleaningTypes = iService.getCleaningTypes();
        List<ServiceType> ironingTypes = iService.getIroningTypes();
        if (cleaningTypes != null && ironingTypes != null){
            iHomeView.getServiceTypes(cleaningTypes, ironingTypes);
        }
    }
}
