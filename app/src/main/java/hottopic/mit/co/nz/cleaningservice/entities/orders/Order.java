package hottopic.mit.co.nz.cleaningservice.entities.orders;

import java.io.Serializable;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;

public class Order implements Serializable{
    private int id;
    private int user_id;
    private String date;
    private SubServiceType subServiceType;
    private String phone;
    private String city;
    private String suburb;
    private String street;
    private int status;
    private float amount;
    private int duration;
    private String start_time;
    private String end_time;
    private int quantity;
    private String feedback;
    private int rating;

    public Order(int user_id,
                 String date,
                 SubServiceType subServiceType,
                 String phone,
                 String city,
                 String suburb,
                 String street,
                 int status,
                 float amount,
                 int quantity,
                 int rating) {
        this.user_id = user_id;
        this.date = date;
        this.subServiceType = subServiceType;
        this.phone = phone;
        this.city = city;
        this.suburb = suburb;
        this.street = street;
        this.status = status;
        this.amount = amount;
        this.quantity = quantity;
        this.rating = rating;
    }

    public Order(int id, int user_id, String date, SubServiceType subServiceType, String phone, String city, String suburb, String street, int status, float amount, int duration, String start_time, String end_time, int quantity, String feedback, int rating) {
        this.id = id;
        this.user_id = user_id;
        this.date = date;
        this.subServiceType = subServiceType;
        this.phone = phone;
        this.city = city;
        this.suburb = suburb;
        this.street = street;
        this.status = status;
        this.amount = amount;
        this.duration = duration;
        this.start_time = start_time;
        this.end_time = end_time;
        this.quantity = quantity;
        this.feedback = feedback;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public SubServiceType getSubServiceType() {
        return subServiceType;
    }

    public void setSubServiceType(SubServiceType subServiceType) {
        this.subServiceType = subServiceType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String formatQuantity(){
        if (Constants.ID_SERVICE_BUFFERING == subServiceType.getId() ||
                Constants.ID_SERVICE_CARPETWASH == subServiceType.getId() ||
                Constants.ID_SERVICE_WATERBLASTING == subServiceType.getId()) {
            return this.quantity + " square meters";
        }else{
            return this.quantity + " square meter";
        }
    }

    public String getAddress() {
        return street + ", " + suburb + ", " + city;
    }
}
