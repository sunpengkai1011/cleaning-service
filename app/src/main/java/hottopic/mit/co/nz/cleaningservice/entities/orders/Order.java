package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;

public class Order implements Serializable{
    private int orderId;
    private int userId;
    private ServiceType serviceType;
    private UAddress uAddress;
    private int date;
    private int orderStatus;
    private String feedback;
    private int duration;
    private float amount;
    private int startTime;
    private int endTime;

    public Order() {
    }

    public Order(int userId, ServiceType serviceType, UAddress uAddress, int date) {
        this.userId = userId;
        this.serviceType = serviceType;
        this.uAddress = uAddress;
        this.date = date;
        feedback = "";
        orderStatus = Constants.STATUS_ORDER_BOOKED;
        duration = 0;
        amount = 0;
        startTime = 0;
        endTime = 0;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public UAddress getuAddress() {
        return uAddress;
    }

    public void setuAddress(UAddress uAddress) {
        this.uAddress = uAddress;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
