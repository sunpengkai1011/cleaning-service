package hottopic.mit.co.nz.cleaningservice.entities.orders.cleaning;

import java.io.Serializable;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;

public class TimerCleaningType extends ServiceType implements Serializable {
    private List<SubOption> subOptions;

    public TimerCleaningType() {
    }

    public TimerCleaningType(int typeId, String typeName, int icon, List<SubOption> subOptions) {
        super(typeId, typeName, icon);
        this.subOptions = subOptions;
    }

    public List<SubOption> getSubOptions() {
        return subOptions;
    }

    public void setSubOptions(List<SubOption> subOptions) {
        this.subOptions = subOptions;
    }
}
