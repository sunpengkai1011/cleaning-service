package hottopic.mit.co.nz.cleaningservice.entities.network;

public class PaymentParams {
    private int user_id;
    private int order_id;
    private float balance;
    private boolean isOrder;
    private String feedback;
    private int status;
    private int rating;
    private float amount;

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public void setOrder(boolean order) {
        isOrder = order;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public float getAmount() {
        return amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
