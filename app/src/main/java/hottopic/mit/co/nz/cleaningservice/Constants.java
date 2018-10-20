package hottopic.mit.co.nz.cleaningservice;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    //Google Map
    public static final String GoogleMap_BASE_URL = "https://maps.googleapis.com/";
    public static final String APIKEY = "AIzaSyA8eJEaPxmkqOSRBC-pNoCQzemiJfIjo5U";
    public static final String DEPARTURE_TIME = "now";
    public static final boolean OPTIMAIZEWAYPOINTS = true;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    //Response code
    public static final int RESPONSE_CODE_SUCCESSFUL = 100;
    public static final int RESPONSE_CODE_FAIL = 101;

    //SharedPreference
    public static final String SP_KEY = "CLEANING_SERVICE";
    public static final String SP_KEY_USERINFO = "USER_INFO";

    //Order Status
    public static final int STATUS_ORDER_BOOKED = 1;
    public static final int STATUS_ORDER_START = 2;
    public static final int STATUS_ORDER_END = 3;
    public static final int STATUS_ORDER_PAID = 4;

    //User Role
    public static final int ROLE_CUSTOMER = 1;
    public static final int ROLE_STAFF = 2;

    public static Map<String, Long> realTime_depart = new HashMap<>();
    public static Map<String, Long> realTime_arrive = new HashMap<>();
}
