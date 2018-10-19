package hottopic.mit.co.nz.cleaningservice.entities;

import java.io.Serializable;

public class PolyLine implements Serializable {
    String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
