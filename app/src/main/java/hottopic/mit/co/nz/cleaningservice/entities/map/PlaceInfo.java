package hottopic.mit.co.nz.cleaningservice.entities.map;

import java.io.Serializable;

public class PlaceInfo implements Serializable {
    private String formatted_address;
    private Geometry geometry;
    private String name;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
