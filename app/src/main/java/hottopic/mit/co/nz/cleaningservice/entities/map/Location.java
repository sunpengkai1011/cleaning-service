package hottopic.mit.co.nz.cleaningservice.entities.map;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Location implements Serializable {
    private float lat;
    private float lng;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public LatLng getLocation(){
        return new LatLng(lat,lng);
    }

    public String getLocationStr(){
        return lat + "," + lng;
    }
}
