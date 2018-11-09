package hottopic.mit.co.nz.cleaningservice.view.home;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceTypes;

public interface IHomeView {
    void getSerivceError(String message);
    void getServiceTypes(ServiceTypes serviceTypes);
}
