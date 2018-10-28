package hottopic.mit.co.nz.cleaningservice.entities.orders.cleaning;

import java.io.Serializable;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;

public class AreaCleaningType extends ServiceType implements Serializable {
    private float unitPrice;

    public AreaCleaningType() {
    }

    public AreaCleaningType(int typeId, String typeName, int icon, float unitPrice) {
        super(typeId, typeName, icon);
        this.unitPrice = unitPrice;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String formatPrice(){
        return "$ " + unitPrice + " /square meter";
    }
}
