package hottopic.mit.co.nz.cleaningservice.entities.orders.cleaning;

import java.io.Serializable;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.users.UAddress;

public class AreaCleaningOrder extends CleaningOrder implements Serializable {
    private int area;

    public AreaCleaningOrder() {
    }

    public AreaCleaningOrder(String userId, ServiceType cleaningType, UAddress uAddress, String date, int area) {
        super(userId, cleaningType, uAddress, date);
        this.area = area;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }
}
