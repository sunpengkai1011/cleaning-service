package hottopic.mit.co.nz.cleaningservice.entities.orders.ironing;

import java.io.Serializable;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;

public class IroningOrder extends Order implements Serializable {
    private int quantity;
    private List<ClothesType> clothesTypes;
    private float amount;

    public IroningOrder() {
    }

    public IroningOrder(String userId, ServiceType serviceType, UAddress uAddress, String date, int quantity, List<ClothesType> clothesTypes, float amount) {
        super(userId, serviceType, uAddress, date);
        this.quantity = quantity;
        this.clothesTypes = clothesTypes;
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<ClothesType> getClothesTypes() {
        return clothesTypes;
    }

    public void setClothesTypes(List<ClothesType> clothesTypes) {
        this.clothesTypes = clothesTypes;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String formatAmount(){
        return "$ " + this.amount;
    }
}
