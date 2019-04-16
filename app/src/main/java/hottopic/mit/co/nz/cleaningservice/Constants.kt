package hottopic.mit.co.nz.cleaningservice

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo

object Constants {
    lateinit var userInfo: UserInfo

    const val SPLASH_DELAY_TIME : Long = 1
    const val EXIT_WAIT_TIME : Long = 2
    const val MAX_SIGNIN_DURATION = 60 * 60 * 24

    //Google Map
    const val GoogleMap_BASE_URL = "https://maps.googleapis.com/"
    const val API_KEY = "AIzaSyA8eJEaPxmkqOSRBC-pNoCQzemiJfIjo5U"
    const val LOCATION_PERMISSION_REQUEST_CODE = 1

    const val CLOSE_TYPE_LOGOUT = 1

    const val TYPE_GET_ORDERS_CUSTOMER = 1
    const val TYPE_GET_ORDERS_STAFF = 2
    const val TYPE_SERVICE_STARTED = 3
    const val TYPE_SERVICE_FINISHED = 4
    const val TYPE_PAYMENT_ORDER = 5
    const val TYPE_PAYMENT_TOP_UP = 6


    const val SP_KEY_LAST_LOGIN_TIMESTAMP = "last_login_time_timestamp"

    //Response code
    const val RESPONSE_CODE_SUCCESSFUL = 200
    const val RESPONSE_CODE_NONE = 401

    //SharedPreference
    const val SP_KEY = "CLEANING_SERVICE"
    const val SP_KEY_USERINFO = "USER_INFO"
    const val SP_KEY_SERVICE_TYPE = "service_type"
    const val SP_KEY_ORDERS = "ORDERS"

    //Intent
    const val KEY_INTENT_USERNAME = "username"
    const val KEY_INTENT_BOOKING = "booking"
    const val KEY_INTENT_ORDER = "order"
    const val KEY_INTENT_ORDER_POSITION = "order_position"
    const val KEY_INTENT_FEEDBACK = "feedback"
    const val KEY_INTENT_TO_PAYMENT = "intent_to_payment"
    const val KEY_INTENT_SERVICETYPE = "service_type"
    const val KEY_INTENT_DISCOUNT = "discount"
    const val KEY_INTENT_LOCATION = "location"
    const val KEY_INTENT_CLOSETYPE = "close_type"
    const val INTENT_REQUEST_ME_TO_EDIT = 200
    const val INTENT_REQUEST_ME_TO_DISCOUNT = 203
    const val INTENT_REQUEST_ODER_TO_CREATE = 201
    const val INTENT_REQUEST_LOGIN_TO_REGISTER = 202
    const val INTENT_REQUEST_ORDER_TO_DETAIL = 204
    const val INTENT_REQUEST_HOME_TO_USER = 207
    const val INTENT_REQUEST_DETAIL_TO_PAYMENT = 205
    const val INTENT_REQUEST_DICOUNT_TO_PAYMENT = 206

    //Service
    const val ID_SERVICE_G_CLEANING = 1
    const val ID_SERVICE_D_CLEANING = 2
    const val ID_SERVICE_BUFFERING = 3
    const val ID_SERVICE_WATERBLASTING = 4
    const val ID_SERVICE_CARPETWASH = 5

    const val SUB_OPTION_NONE = 0
    const val SUB_OPTION_1BEDROOM = 1
    const val SUB_OPTION_2BEDROOMS = 2
    const val SUB_OPTION_3BEDROOMS = 3
    const val SUB_OPTION_4BEDROOMS = 4

    const val ID_SERVICE_G_INRONING = 6
    const val ID_SERVICE_S_INRONING = 7

    const val ID_CLOTHES_SHIRT = 400
    const val ID_CLOTHES_JACKET = 401
    const val ID_CLOTHES_LONG_DRESS = 402
    const val ID_CLOTHES_SCHOOL_UNIFORM = 403

    //Order Status
    const val STATUS_ORDER_BOOKED = 0
    const val STATUS_ORDER_STARTED = 1
    const val STATUS_ORDER_FINISHED = 2
    const val STATUS_ORDER_PAID = 3

    //User Role
    const val ROLE_CUSTOMER = 1
    const val ROLE_STAFF = 2

    //Payment
    const val TYPE_PAYMENT_CARD = 0
    const val TYPE_PAYMENT_BALANCE = 1

    //Handler
    const val WHAT_ADVERTISEMENT = 0
    const val WHAT_EXIT = 1

    //Clother
    const val ADAPTER_CLOTHES_BOOKING = 1
    const val ADAPTER_CLOTHES_DETAIL = 2

    const val TYPE_G_T_SHIRT = 17
    const val TYPE_G_JACKET = 18
    const val TYPE_G_LONG_DRESS = 19
    const val TYPE_G_SCHOOL_UNIFORM = 20
    const val TYPE_S_T_SHIRT = 21
    const val TYPE_S_JACKET = 22
    const val TYPE_S_LONG_DRESS = 23
    const val TYPE_S_SCHOOL_UNIFORM = 24

    const val TYPE_G_CLEANING = 1
    const val TYPE_G_IRONING = 6
    const val TYPE_D_CLEANING = 2
    const val TYPE_S_IRONING = 7
    const val TYPE_BUFFERING = 3
    const val TYPE_WATER_BLASTING = 4
    const val TYPE_CARPET_WASHING = 5
}
