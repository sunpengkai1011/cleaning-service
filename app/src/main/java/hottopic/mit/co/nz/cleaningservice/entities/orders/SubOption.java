package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;

public class SubOption implements Serializable{
    private int subOptionId;
    private String subOptionName;
    private float unitPrice;

    public SubOption() {
    }

    public SubOption(int subOptionId, String subOptionName, float unitPrice) {
        this.subOptionId = subOptionId;
        this.subOptionName = subOptionName;
        this.unitPrice = unitPrice;
    }

    public int getSubOptionId() {
        return subOptionId;
    }

    public void setSubOptionId(int subOptionId) {
        this.subOptionId = subOptionId;
    }

    public String getSubOptionName() {
        return subOptionName;
    }

    public void setSubOptionName(String subOptionName) {
        this.subOptionName = subOptionName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String formatPrice(){
        return "$ " + unitPrice + " /per hour";
    }
}
