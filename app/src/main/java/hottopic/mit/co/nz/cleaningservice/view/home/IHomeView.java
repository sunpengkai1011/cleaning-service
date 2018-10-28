package hottopic.mit.co.nz.cleaningservice.view.home;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;

public interface IHomeView {
    void getServiceTypes(List<ServiceType> cleaningTypes, List<ServiceType> ironingTypes);
}
