package me.yashtrivedi.ideal_succotash;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class Constants {

    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String FIREBASE_LOCATION_RIDES = "availableRides";
    public static final String FIREBASE_LOCATION_UID_MAP = "uidMap";
    public static final String FIREBASE_LOCATION_REQUEST_RIDE = "rideRequest";
    public static final String FIREBASE_LOCATION_USER_REQUEST = "userRequests";
    public static final String FIREBASE_LOCATION_CHATS = "chats";

    /*Firebase URL*/
    public static final String FIREBASE_URL = "https://ideal-succotash.firebaseio.com";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL.concat("/").concat(FIREBASE_LOCATION_USERS);
    public static final String FIREBASE_URL_RIDES = FIREBASE_URL.concat("/").concat(FIREBASE_LOCATION_RIDES);
    public static final String FIREBASE_URL_USER_REQUEST = FIREBASE_URL.concat("/").concat(FIREBASE_LOCATION_USER_REQUEST);
    public static final String FIREBASE_URL_CHATS = FIREBASE_URL.concat("/").concat(FIREBASE_LOCATION_CHATS);

    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    public static final String KEY_GOOGLE_EMAIL = "g_mail";
    public static final String KEY_PROVIDER = "provider";
    public static final String KEY_ENCODED_EMAIL = "encemail";
    public static final String KEY_NAME = "profile_name";
    public static final String KEY_OFFERED = "ride_offered";

    public static final String PROVIDER_DATA_DISPLAY_NAME = "disp_name";

    public static final String GOOGLE_PROVIDER = "google";

    public static final int RIDE_REQUEST_WAITING = 0;
    public static final int RIDE_REQUEST_REJECTED = -1;
    public static final int RIDE_REQUEST_ACCEPTED = 1;

    public static final String USER_NAME = "userName";
    public static final String TO_NIRMA = "toNirma";
    public static final String CAR_NO = "carNo";
    public static final String CAR_CAPACITY = "carCapacity";
    public static final String AREA = "area";
    public static final String REQUEST_STATUS = "status";
    public static final String REQUESTED_USER = "reqUser";

    public static final String ACTION_CANCEL_RIDE = "cancelRide";
    public static final String ACTION_REJECT_RIDE = "rejectRide";
    public static final String ACTION_ACCEPT_RIDE = "acceptRide";

    public static final String THREAD_NAME = "name";
    public static final String THREAD_EMAIL = "email";
    public static final String THREAD_TIME = "time";
    public static final String THREAD_READ = "read";
    public static final String THREAD_UNREAD_COUNT = "unreadCount";
    public static final String THREAD_LAST_MSG = "msg";

    public static final int MESSAGE_VIEW_OWN = 1;
    public static final int MESSAGE_VIEW_OTHERS = 2;
    public static final String CONVERSATION_PUSH_ID = "pushID";

}
