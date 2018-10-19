package hottopic.mit.co.nz.cleaningservice.entities.request;

import java.io.Serializable;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.map.RouteInfo;

public class RouteResponse implements Serializable{
    private List<RouteInfo> routes;

    public List<RouteInfo> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteInfo> routes) {
        this.routes = routes;
    }
}