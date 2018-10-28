package hottopic.mit.co.nz.cleaningservice.entities.orders.ironing;

import java.io.Serializable;

public class ClothesType implements Serializable {
    private int clothesId;
    private int icon;
    private String clothesName;
    private float unitPrice;
    private int quantity;

    public ClothesType() { }

    public ClothesType(int clothesId, int icon, String clothesName, float unitPrice) {
        this.clothesId = clothesId;
        this.icon = icon;
        this.clothesName = clothesName;
        this.unitPrice = unitPrice;
        this.quantity = 0;
    }

    public int getClothesId() {
        return clothesId;
    }

    public void setClothesId(int clothesId) {
        this.clothesId = clothesId;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getClothesName() {
        return clothesName;
    }

    public void setClothesName(String clothesName) {
        this.clothesName = clothesName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String formatPrice(){
        return "$ " + this.unitPrice + " /piece";
    }

    public String formatPriceForBooking(){
        return "($" + this.unitPrice + "/piece)";
    }
}
