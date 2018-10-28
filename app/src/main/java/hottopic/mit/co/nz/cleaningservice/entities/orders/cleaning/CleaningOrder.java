package hottopic.mit.co.nz.cleaningservice.entities.orders.cleaning;

import java.io.Serializable;

import hottopic.mit.co.nz.cleaningservice.entities.orders.Order;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;

public class CleaningOrder extends Order implements Serializable {
    private String feedback;
    private float amount;


    public CleaningOrder() {
    }

    public CleaningOrder(String userId, ServiceType cleaningType, UAddress uAddress, String date) {
        super(userId, cleaningType, uAddress, date);
        this.amount = 0;
        this.feedback = "";
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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
