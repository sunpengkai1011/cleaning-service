package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;

public class Order implements Serializable{
    private int orderId;
    private String userId;
    private String date;
    private ServiceType serviceType;
    private UAddress uAddress;
    private int status;

    public Order() {
    }

    public Order(String userId, ServiceType serviceType, UAddress uAddress, String date) {
        this.userId = userId;
        this.serviceType = serviceType;
        this.uAddress = uAddress;
        this.date = date;
        this.status = Constants.STATUS_ORDER_BOOKED;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
