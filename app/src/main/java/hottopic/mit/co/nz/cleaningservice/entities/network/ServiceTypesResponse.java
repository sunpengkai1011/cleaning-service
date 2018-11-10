package hottopic.mit.co.nz.cleaningservice.entities.network;

import java.io.Serializable;
import java.util.List;

public class ServiceTypesResponse extends BaseResponse implements Serializable {
    List<ProductResponse> serviceTypes;

    public List<ProductResponse> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ProductResponse> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }
}
