package hottopic.mit.co.nz.cleaningservice.entities.orders.cleaning;

import java.io.Serializable;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;

public class TimerCleaningOrder extends CleaningOrder implements Serializable {
    private SubOption subOption;
    private int duration;
    private String startTime;
    private String endTime;

    public TimerCleaningOrder() {
    }

    public TimerCleaningOrder(String userId, ServiceType cleaningType, UAddress uAddress, String date, SubOption subOption) {
        super(userId, cleaningType, uAddress, date);
        this.subOption = subOption;
        duration = 0;
        startTime = "";
        endTime = "";
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
}
