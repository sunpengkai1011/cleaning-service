package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;
import java.util.List;

public class ServiceType implements Serializable{
    private int typeId;
    private String typeName;
    private int icon;

    private float unitPrice;

    private List<SubOption> subOptions;

    private List<ClothesType> clothesTypes;
    private float bulkDiscount;

    public ServiceType() {
    }

    public ServiceType(int typeId, String typeName, int icon, float unitPrice) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.icon = icon;
        this.unitPrice = unitPrice;
    }

    public ServiceType(int typeId, String typeName, int icon, List<SubOption> subOptions) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.icon = icon;
        this.subOptions = subOptions;
    }

    public ServiceType(int typeId, String typeName, int icon, List<ClothesType> clothesTypes, float bulkDiscount) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.icon = icon;
        this.clothesTypes = clothesTypes;
        this.bulkDiscount = bulkDiscount;
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

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<SubOption> getSubOptions() {
        return subOptions;
    }

    public void setSubOptions(List<SubOption> subOptions) {
        this.subOptions = subOptions;
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

    public String formatPrice(){
        return "$ " + unitPrice + " /square meter";
    }
}
