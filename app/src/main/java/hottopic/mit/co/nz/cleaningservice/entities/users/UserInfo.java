package hottopic.mit.co.nz.cleaningservice.entities.users;

import java.io.Serializable;

import hottopic.mit.co.nz.cleaningservice.Constants;

public class UserInfo implements Serializable{
    private int userId;
    private String userName;
    private String password;
    private String phoneNumber;
    private String email;
    private UAddress uAddress;
    private UserRole userRole;
    private float balance;

    public UserInfo() {}

    public UserInfo(String userName, String password, String phoneNumber, String email, UAddress uAddress) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.uAddress = uAddress;
        userRole = new UserRole(Constants.ROLE_CUSTOMER);
        balance = 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UAddress getuAddress() {
        return uAddress;
    }

    public void setuAddress(UAddress uAddress) {
        this.uAddress = uAddress;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
