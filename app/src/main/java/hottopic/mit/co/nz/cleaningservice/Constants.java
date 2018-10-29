package hottopic.mit.co.nz.cleaningservice;

import java.util.HashMap;
import java.util.Map;

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;

public class Constants {
    //Google Map
    public static final String GoogleMap_BASE_URL = "https://maps.googleapis.com/";
    public static final String APIKEY = "AIzaSyA8eJEaPxmkqOSRBC-pNoCQzemiJfIjo5U";
    public static final String DEPARTURE_TIME = "now";
    public static final boolean OPTIMAIZEWAYPOINTS = true;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public static final int LOGOUT = 1000;

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
    public static final String KEY_INTENT_FEEDBACK = "feedback";
    public static final String KEY_INTENT_TO_PAYMENT = "intent_to_payment";
    public static final String KEY_INTENT_SERVICETYPE = "service_type";
    public static final String KEY_INTENT_DISCOUNT = "discount";
    public static final int INTENT_REQUEST_ME_TO_EDIT = 200;
    public static final int INTENT_REQUEST_ME_TO_DISCOUNT = 203;
    public static final int INTENT_REQUEST_ODER_TO_CREATE = 201;
    public static final int INTENT_REQUEST_LOGIN_TO_REGISTER = 202;
    public static final int INTENT_REQUEST_ORDER_TO_DETAIL = 204;
    public static final int INTENT_REQUEST_HOME_TO_USER = 207;
    public static final int INTENT_REQUEST_DETAIL_TO_PAYMENT = 205;
    public static final int INTENT_REQUEST_DICOUNT_TO_PAYMENT = 206;

    //Service
    public static final int ID_SERVICE_G_CLEANING = 300;
    public static final int ID_SERVICE_D_CLEANING = 301;
    public static final int ID_SERVICE_BUFFERING = 302;
    public static final int ID_SERVICE_WATERBLASTING = 303;
    public static final int ID_SERVICE_CARPETWASH = 304;

    public static final int SUB_OPTION_NONE = 0;
    public static final int SUB_OPTION_1BEDROOM = 1;
    public static final int SUB_OPTION_2BEDROOMS = 2;
    public static final int SUB_OPTION_3BEDROOMS = 3;
    public static final int SUB_OPTION_4BEDROOMS = 4;

    public static final int ID_SERVICE_G_INRONING = 305;
    public static final int ID_SERVICE_S_INRONING = 306;

    public static final int ID_CLOTHES_SHIRT = 400;
    public static final int ID_CLOTHES_JACKET = 401;
    public static final int ID_CLOTHES_LONG_DRESS = 402;
    public static final int ID_CLOTHES_SCHOOL_UNIFORM = 403;

    //Order Status
    public static final int STATUS_ORDER_BOOKED = 1;
    public static final int STATUS_ORDER_STARTED = 2;
    public static final int STATUS_ORDER_FINISHED = 3;
    public static final int STATUS_ORDER_PAID = 4;

    //User Role
    public static final int ROLE_CUSTOMER = 1;
    public static final int ROLE_STAFF = 2;

    //Payment
    public static final int TYPE_PAYMENT_CARD = 0;
    public static final int TYPE_PAYMENT_BALANCE = 1;

    //Handler
    public static final int WHAT_ADVERTISEMENT = 0;
    public static final int WHAT_EXIT = 1;

    //Clother
    public static final int ADAPTER_CLOTHES_BOOKING = 1;
    public static final int ADAPTER_CLOTHES_DETAIL = 2;

    public static Map<String, Long> realTime_depart = new HashMap<>();
    public static Map<String, Long> realTime_arrive = new HashMap<>();
}
