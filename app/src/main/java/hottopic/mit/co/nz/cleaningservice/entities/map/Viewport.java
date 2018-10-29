package hottopic.mit.co.nz.cleaningservice.entities.map;

import java.io.Serializable;

public class Viewport implements Serializable {
    private Location northeast;
    private Location southwest;

    public Location getNortheast() {
        return northeast;
    }

    public void setNortheast(Location northeast) {
        this.northeast = northeast;
    }

    public Location getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Location southwest) {
        this.southwest = southwest;
    }
}
