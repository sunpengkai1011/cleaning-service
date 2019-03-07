package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class SubServiceType implements Serializable {
    private int id;
    private int main_id;
    private String type_name;
    private float bulk_discount;
    private int icon;
    private List<ServiceProduct> products = new ArrayList<>();

    public SubServiceType(int id, int main_id, String type_name, float bulk_discount) {
        this.id = id;
        this.main_id = main_id;
        this.type_name = type_name;
        this.bulk_discount = bulk_discount;
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

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public float getBulk_discount() {
        return bulk_discount;
    }

    public void setBulk_discount(float bulk_discount) {
        this.bulk_discount = bulk_discount;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public List<ServiceProduct> getProducts() {
        return products;
    }

    public void addProduct(ServiceProduct serviceProduct) {
        products.add(serviceProduct);
    }

    public void setProducts(List<ServiceProduct> products) {
        this.products = products;
    }

    public String formatBulkDiscount(){
        return GeneralUtil.formatDiscount(this.bulk_discount);
    }
}
