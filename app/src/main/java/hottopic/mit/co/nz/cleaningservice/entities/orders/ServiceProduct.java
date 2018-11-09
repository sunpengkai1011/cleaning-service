package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;

public class ServiceProduct implements Serializable {
    private int id;
    private int main_id;
    private int sub_id;
    private String product_name;
    private int icon;
    private float unit_price;
    private String unit;


    public ServiceProduct(int id, int main_id, int sub_id, String product_name, float unit_price, String unit) {
        this.id = id;
        this.main_id = main_id;
        this.sub_id = sub_id;
        this.product_name = product_name;
        this.unit_price = unit_price;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMain_id() {
        return main_id;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }

    public int getSub_id() {
        return sub_id;
    }

    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
