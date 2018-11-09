package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;
import java.util.List;

public class ServiceTypes implements Serializable {
    List<MainServiceType> mainServiceTypes;

    public List<MainServiceType> getMainServiceTypes() {
        return mainServiceTypes;
    }

    public void setMainServiceTypes(List<MainServiceType> mainServiceTypes) {
        this.mainServiceTypes = mainServiceTypes;
    }
}
