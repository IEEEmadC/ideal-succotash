package me.yashtrivedi.ideal_succotash;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class Constants {

    static final String FIREBASE_LOCATION_USERS = "users";
    static final String FIREBASE_LOCATION_RIDES = "availableRides";
    static final String FIREBASE_LOCATION_UID_MAP = "uidMap";
    static final String FIREBASE_LOCATION_REQUEST_RIDE = "rideRequest";

    /*Firebase URL*/
    static final String FIREBASE_URL = "https://ideal-succotash.firebaseio.com";
    static final String FIREBASE_URL_USERS = FIREBASE_URL.concat("/").concat(FIREBASE_LOCATION_USERS);
    static final String FIREBASE_URL_RIDES = FIREBASE_URL.concat("/").concat(FIREBASE_LOCATION_RIDES);
    static final String FIREBASE_URL_REQUEST_RIDE = FIREBASE_URL.concat("/").concat(FIREBASE_LOCATION_REQUEST_RIDE);

    static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    static final String KEY_GOOGLE_EMAIL = "g_mail";
    static final String KEY_PROVIDER = "provider";
    static final String KEY_ENCODED_EMAIL = "encemail";
    static final String KEY_NAME = "profile_name";
    static final String KEY_OFFERED = "ride_offered";

    static final String PROVIDER_DATA_DISPLAY_NAME = "disp_name";

    static final String GOOGLE_PROVIDER = "google";

    static final int RIDE_REQUEST_WAITING = 0;
    static final int RIDE_REQUEST_REJECTED = -1;
    static final int RIDE_REQUEST_ACCEPTED = 1;

    static final String USER_NAME="userName";
    static final String TO_NIRMA = "toNirma";
    static final String CAR_NO = "carNo";
    static final String CAR_CAPACITY = "carCapacity";
    static final String AREA = "area";
    static final String REQUEST_STATUS = "status";
    static final String REQUESTED_USER = "reqUser";

}
