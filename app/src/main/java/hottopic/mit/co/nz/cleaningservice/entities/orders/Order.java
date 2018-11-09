package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class Order implements Serializable{
    private int orderId;
    private int userId;
    private String date;
    private ServiceType serviceType;
    private UAddress uAddress;
    private int status;
    private String phone;

    private String feedback;
    private float amount;

    private int area;

    private SubOption subOption;
    private int duration;
    private String startTime;
    private String endTime;

    private int quantity;
    private List<ClothesType> clothesTypes;
    private int rating;


    public Order() {
    }

    public Order(int userId, ServiceType serviceType, String phone, String date, UAddress uAddress, int area, float amount) {
        this.userId = userId;
        this.date = date;
        this.serviceType = serviceType;
        this.uAddress = uAddress;
        this.area = area;
        this.amount = amount;
        this.phone = phone;
        this.feedback = "";
        this.status = Constants.STATUS_ORDER_FINISHED;
        this.rating = 10;
    }

    public Order(int userId, ServiceType serviceType, String phone, String date, UAddress uAddress, SubOption subOption) {
        this.userId = userId;
        this.date = date;
        this.serviceType = serviceType;
        this.uAddress = uAddress;
        this.subOption = subOption;
        this.duration = 0;
        this.phone = phone;
        this.startTime = "";
        this.endTime = "";
        this.status = Constants.STATUS_ORDER_BOOKED;
        this.rating = 10;
    }

    public Order(int userId, ServiceType serviceType, String phone, String date, UAddress uAddress, float amount, int quantity, List<ClothesType> clothesTypes) {
        this.userId = userId;
        this.date = date;
        this.serviceType = serviceType;
        this.uAddress = uAddress;
        this.amount = amount;
        this.quantity = quantity;
        this.phone = phone;
        this.clothesTypes = clothesTypes;
        this.status = Constants.STATUS_ORDER_FINISHED;
        this.rating = 10;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public SubOption getSubOption() {
        return subOption;
    }

    public void setSubOption(SubOption subOption) {
        this.subOption = subOption;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String formatDuration(){
        if (this.duration > 1){
            return this.duration + " Hours";
        }else{
            return this.duration + " Hour";
        }
    }

    public String formatAmount(){
        return "$ " + GeneralUtil.priceFormat(this.amount);
    }

    public String formatArea(){
        if (this.area > 1) {
            return this.area + " square meters";
        }else{
            return this.area + " square meter";
        }
    }
}
