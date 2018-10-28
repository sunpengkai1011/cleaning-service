package hottopic.mit.co.nz.cleaningservice.entities.orders.ironing;

import java.io.Serializable;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;

public class IroningType extends ServiceType implements Serializable{
    private List<ClothesType> clothesTypes;
    private float bulkDiscount;

    public IroningType() {
    }

    public IroningType(int typeId, String typeName, int icon, List<ClothesType> clothesTypes, float bulkDiscount) {
        super(typeId, typeName, icon);
        this.clothesTypes = clothesTypes;
        this.bulkDiscount = bulkDiscount;
    }

    public List<ClothesType> getClothesTypes() {
        return clothesTypes;
    }

    public void setClothesTypes(List<ClothesType> clothesTypes) {
        this.clothesTypes = clothesTypes;
    }

    public float getBulkDiscount() {
        return bulkDiscount;
    }

    public void setBulkDiscount(float bulkDiscount) {
        this.bulkDiscount = bulkDiscount;
    }

    public String formatBulkDiscount(){
        return "More than 20 pieces will enjoy the " + this.bulkDiscount + " discount.";
    }
}
