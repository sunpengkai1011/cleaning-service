package hottopic.mit.co.nz.cleaningservice.entities.users;

import java.io.Serializable;

public class UAddress implements Serializable{
    private String city;
    private String suburb;
    private String street;

    public UAddress() {
    }

    public UAddress(String city, String suburb, String street) {
        this.city = city;
        this.suburb = suburb;
        this.street = street;
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
}
