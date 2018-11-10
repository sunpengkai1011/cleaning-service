package hottopic.mit.co.nz.cleaningservice.entities.network;

import java.io.Serializable;

public class ProductResponse implements Serializable {
    private int id;
    private int sub_id;
    private int main_id;
    private String sub_type_name;
    private String main_type_name;
    private String product_name;
    private float unit_price;
    private String unit;
    private float bulk_discount;
    private String icon;
    private String product_icon;

    public int getId() {
        return id;
    }

    public int getSub_id() {
        return sub_id;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }

    public int getMain_id() {
        return main_id;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }

    public String getSub_type_name() {
        return sub_type_name;
    }

    public void setSub_type_name(String sub_type_name) {
        this.sub_type_name = sub_type_name;
    }

    public String getMain_type_name() {
        return main_type_name;
    }

    public void setMain_type_name(String main_type_name) {
        this.main_type_name = main_type_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getBulk_discount() {
        return bulk_discount;
    }

    public void setBulk_discount(float bulk_discount) {
        this.bulk_discount = bulk_discount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getProduct_icon() {
        return product_icon;
    }

    public void setProduct_icon(String product_icon) {
        this.product_icon = product_icon;
    }
}
