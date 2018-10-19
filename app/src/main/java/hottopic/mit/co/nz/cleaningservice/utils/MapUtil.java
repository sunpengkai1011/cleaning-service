package hottopic.mit.co.nz.cleaningservice.utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.map.Bound;
import hottopic.mit.co.nz.cleaningservice.entities.map.LegInfo;
import hottopic.mit.co.nz.cleaningservice.entities.map.RouteInfo;
import hottopic.mit.co.nz.cleaningservice.entities.map.StepInfo;


public class MapUtil {

    public static GoogleMap addMarkers(GoogleMap map, List<LegInfo> legs){
        for (LegInfo info: legs) {
            map.addMarker(new MarkerOptions().position(info.getStart_location().getLocation()).snippet(info.getStart_address()));
        }
        LegInfo last = legs.get(legs.size() - 1);
        map.addMarker(new MarkerOptions().position(last.getEnd_location().getLocation()).snippet(last.getEnd_address()));
        return map;
    }

    public static GoogleMap displayArea(GoogleMap map, Context context, Bound bound, int padding){
        LatLngBounds bounds = new LatLngBounds.Builder().include(bound.getNortheast().getLocation()).include(bound.getSouthwest().getLocation()).build();
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, GeneralUtil.dip2px(context, padding)));
        return map;
    }

    @SuppressLint("ResourceType")
    public static GoogleMap drawRoute(GoogleMap map, Context context, List<LegInfo> legs){
        List<LatLng> points = new ArrayList<>();

        for(LegInfo info: legs){
            for(StepInfo stepInfo: info.getSteps()){
               points.addAll(decodePoly(stepInfo.getPolyline().getPoints()));
            }
        }
        PolylineOptions options = new PolylineOptions();
        options.addAll(points).color(Color.parseColor(context.getResources().getString(R.color.route))).width(GeneralUtil.dip2px(context, 7));
        map.addPolyline(options);
        return map;
    }

    @SuppressLint("ResourceType")
    public static GoogleMap drawRoute(GoogleMap map, Context context, List<LegInfo> legs, int position){
        List<LatLng> points = new ArrayList<>();
        for(StepInfo stepInfo: legs.get(position).getSteps()){
            points.addAll(decodePoly(stepInfo.getPolyline().getPoints()));
        }
        PolylineOptions options = new PolylineOptions();
        options.addAll(points).color(Color.parseColor(context.getResources().getString(R.color.route))).width(GeneralUtil.dip2px(context, 7));
        map.addPolyline(options);
        return map;
    }

    public static void intentToGoogleMap(Context context, RouteInfo routeInfo){
        String uri = "https://www.google.com/maps/dir/?api=1&";
        for(int i = 0; i < routeInfo.getLegs().size(); i++){
            if(i == 0){
                uri = uri + "origin=" + routeInfo.getLegs().get(i).getStart_location().getLocationStr() + "&waypoints=";
            }if (i == routeInfo.getLegs().size() - 1){
                uri = uri + "&destination=" + routeInfo.getLegs().get(i).getEnd_location().getLocationStr();
            }else{
                if (i == routeInfo.getLegs().size() - 2) {
                    uri = uri + routeInfo.getLegs().get(i).getEnd_location().getLocationStr();
                }else{
                    uri = uri + routeInfo.getLegs().get(i).getEnd_location().getLocationStr() + "|";
                }
            }
        }
        uri = uri + "&travelmode=driving";
        Uri gmmIntentUri = Uri.parse(uri);
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        intent.setPackage("com.google.android.apps.maps");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                context.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(context, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
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
