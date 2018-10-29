package hottopic.mit.co.nz.cleaningservice.entities.map;

import java.io.Serializable;

public class Geometry implements Serializable {
    private Location location;
    private Viewport viewport;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
