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
    public static final String SP_KEY_ORDERS = "ORDERS";

    //Intent
    public static final String KEY_INTENT_USERINFO = "userinfo";
    public static final String KEY_INTENT_BOOKING = "booking";
    public static final String KEY_INTENT_ORDER = "order";
    public static final String KEY_INTENT_ORDER_POSITION = "order_position";
    public static final int INTENT_REQUEST_ME_TO_EDIT = 200;
    public static final int INTENT_REQUEST_ME_TO_DISCOUNT = 203;
    public static final int INTENT_REQUEST_ODER_TO_CREATE = 201;
    public static final int INTENT_REQUEST_LOGIN_TO_REGISTER = 202;
    public static final int INTENT_REQUEST_ORDER_TO_DETAIL = 204;

    //Order Status
    public static final int STATUS_ORDER_BOOKED = 1;
    public static final int STATUS_ORDER_STARTED = 2;
    public static final int STATUS_ORDER_FINISHED = 3;
    public static final int STATUS_ORDER_PAID = 4;

    //User Role
    public static final int ROLE_CUSTOMER = 1;
    public static final int ROLE_STAFF = 2;

    public static Map<String, Long> realTime_depart = new HashMap<>();
    public static Map<String, Long> realTime_arrive = new HashMap<>();
}
