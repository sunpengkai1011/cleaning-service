package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;

public class ServiceType implements Serializable{
    private int typeId;
    private String typeName;
    private float pricePerHour;

    public ServiceType() {
    }

    public ServiceType(int typeId, String typeName, float pricePerHour) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.pricePerHour = pricePerHour;
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

    public float getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(float pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
