package hottopic.mit.co.nz.cleaningservice.presenter.home;

import hottopic.mit.co.nz.cleaningservice.entities.network.ServiceTypesResponse;

public interface IHomePresenter {
    void getServiceTypes();
    void serviceTypesResult(ServiceTypesResponse response);
}
