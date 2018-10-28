package hottopic.mit.co.nz.cleaningservice.model.order;

import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;

public interface IService {
    List<ServiceType> getCleaningTypes();
    List<ServiceType> getIroningTypes();
}
