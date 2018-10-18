package hottopic.mit.co.nz.cleaningservice.entities;

import java.io.Serializable;
import java.util.List;

public class RequestAddressInfo implements Serializable {
    private String origin;
    private String destination;
    private List<String> waypoints;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<String> waypoints) {
        this.waypoints = waypoints;
    }

    public String getWaypointsStr(){
        String waypiont = "optimize:true|";
        for (String item: waypoints){
            waypiont = waypiont + item + "|";
        }
        String result = waypiont.substring(0, waypiont.length()-1);
        result = result.replace(" ", "+");
        result = result.replace(",", "+");
        return result;
    }
}
