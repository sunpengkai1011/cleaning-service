package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;

public class Discount implements Serializable {
    private int id;
    private float balance;
    private float price;

    public Discount() {
    }

    public Discount(int id, float balance, float price) {
        this.id = id;
        this.balance = balance;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String formatPrice(){
        return "$ " + (int)this.price;
    }

    public String formatBalance(){
        return "$ " + (int)this.balance;
    }
}
