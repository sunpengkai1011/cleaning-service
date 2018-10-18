package hottopic.mit.co.nz.cleaningservice.entities;

import java.io.Serializable;
import java.util.List;

public class RouteInfo implements Serializable{
    private Bound bounds;
    private List<LegInfo> legs;
    private List<Integer> waypoint_order;

    public List<LegInfo> getLegs() {
        return legs;
    }

    public void setLegs(List<LegInfo> legs) {
        this.legs = legs;
    }

    public List<Integer> getWaypoint_order() {
        return waypoint_order;
    }

    public void setWaypoint_order(List<Integer> waypoint_order) {
        this.waypoint_order = waypoint_order;
    }

    public Bound getBounds() {
        return bounds;
    }

    public void setBounds(Bound bounds) {
        this.bounds = bounds;
    }
}
