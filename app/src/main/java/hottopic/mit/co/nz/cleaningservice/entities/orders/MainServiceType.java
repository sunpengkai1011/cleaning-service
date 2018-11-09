package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;
import java.util.List;

public class MainServiceType implements Serializable {
    private int id;
    private String type_name;
    private List<SubServiceType> subServiceTypes;

    public MainServiceType(int id, String type_name, List<SubServiceType> subServiceTypes) {
        this.id = id;
        this.type_name = type_name;
        this.subServiceTypes = subServiceTypes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public List<SubServiceType> getSubServiceTypes() {
        return subServiceTypes;
    }

    public void setSubServiceTypes(List<SubServiceType> subServiceTypes) {
        this.subServiceTypes = subServiceTypes;
    }
}
