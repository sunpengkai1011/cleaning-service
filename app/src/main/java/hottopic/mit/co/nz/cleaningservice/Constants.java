package hottopic.mit.co.nz.cleaningservice;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String GoogleMap_BASE_URL = "https://maps.googleapis.com/";
    public static final String APIKEY = "AIzaSyA8eJEaPxmkqOSRBC-pNoCQzemiJfIjo5U";
    public static final String DEPARTURE_TIME = "now";
    public static final boolean OPTIMAIZEWAYPOINTS = true;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public static Map<String, Long> realTime_depart = new HashMap<>();
    public static Map<String, Long> realTime_arrive = new HashMap<>();
}
