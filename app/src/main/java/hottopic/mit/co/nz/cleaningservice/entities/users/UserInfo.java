package hottopic.mit.co.nz.cleaningservice.entities.users;

import java.io.Serializable;

public class UserInfo implements Serializable{
    private int id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String city;
    private String suburb;
    private String street;
    private int role_id;
    private String role_name;
    private float balance;

    public UserInfo(String username, String password, String phone,
                    String email, String city, String suburb, String street,
                    int role_id, float balance) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.suburb = suburb;
        this.street = street;
        this.role_id = role_id;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public int getUserId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String formatBalance(){
        return "$ " + this.balance;
    }

    public String getAddress() {
        return street + ", " + suburb + ", " + city;
    }
}
