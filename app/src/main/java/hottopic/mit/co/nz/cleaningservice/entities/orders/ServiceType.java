package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;

public class ServiceType implements Serializable{
    private int typeId;
    private String typeName;
    private int icon;

    public ServiceType() {
    }

    public ServiceType(int typeId, String typeName, int icon) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.icon = icon;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
