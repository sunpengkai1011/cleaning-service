package hottopic.mit.co.nz.cleaningservice.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationUtil {

    /**
     * Get the lng and lat
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getMyLocationAddress(Context context) {
        Location location;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                return getAddress(context, getLngAndLatWithNetwork(context));
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return getAddress(context, location);
    }


    @SuppressLint("MissingPermission")
    private static Location getLngAndLatWithNetwork(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        return location;
    }


    static LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            Log.i("ABC", "Changed");
        }
    };

    private static String getAddress(Context context, Location location) {
        String currentPosition = "";
        Geocoder gc = new Geocoder(context, Locale.ENGLISH);
        try {
            List<Address> locationList = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (locationList != null) {
                Address address = locationList.get(0);
                currentPosition = address.getFeatureName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentPosition;
    }
}
