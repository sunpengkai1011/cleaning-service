package hottopic.mit.co.nz.cleaningservice.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.entities.map.PlaceInfo;
import hottopic.mit.co.nz.cleaningservice.entities.map.Viewport;


public class MapUtil {

    public static GoogleMap addMark(GoogleMap map, PlaceInfo placeInfo){
        map.addMarker(new MarkerOptions().position(placeInfo.getGeometry().getLocation().getLocation()).title(placeInfo.getFormatted_address()));
        return map;
    }

    public static GoogleMap displayArea(GoogleMap map, Context context, Viewport bound, int padding){
        LatLngBounds bounds = new LatLngBounds.Builder().include(bound.getNortheast().getLocation()).include(bound.getSouthwest().getLocation()).build();
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, GeneralUtil.dip2px(context, padding)));
        return map;
    }

    public static void intentToGoogleMap(Context context, String location){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location + "&travelmode=driving");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

    /**
     * Method to decode polyline points
     * */
    private static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
