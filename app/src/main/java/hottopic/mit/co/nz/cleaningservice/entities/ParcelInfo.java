package hottopic.mit.co.nz.cleaningservice.entities;

import java.io.Serializable;

public class ParcelInfo implements Serializable {
    private String trackId;
    private String deliveryAddress;
    private String recipientName;
    private String recipientPhoneNum;

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhoneNum() {
        return recipientPhoneNum;
    }

    public void setRecipientPhoneNum(String recipientPhoneNum) {
        this.recipientPhoneNum = recipientPhoneNum;
    }
}
