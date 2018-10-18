package hottopic.mit.co.nz.cleaningservice.entities;

import java.io.Serializable;

public class StepInfo implements Serializable {
    private Distance distance;
    private Duration duration;
    private Location end_location;
    private Location start_location;
    private PLine polyline;

    public PLine getPolyline() {
        return polyline;
    }

    public void setPolyline(PLine polyline) {
        this.polyline = polyline;
    }

    public Location getStart_location() {
        return start_location;
    }

    public void setStart_location(Location start_location) {
        this.start_location = start_location;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Location getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Location end_location) {
        this.end_location = end_location;
    }
}
