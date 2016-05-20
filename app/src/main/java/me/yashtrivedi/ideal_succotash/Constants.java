package me.yashtrivedi.ideal_succotash;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class Constants {

    static final String FIREBASE_LOCATION_USERS = "users";
    static final String FIREBASE_LOCATION_RIDES = "availableRides";
    static final String FIREBASE_LOCATION_UID_MAP = "uidMap";

    /*Firebase URL*/
    static final String FIREBASE_URL = "https://ideal-succotash.firebaseio.com";
    static final String FIREBASE_URL_USERS = FIREBASE_URL.concat("/").concat(FIREBASE_LOCATION_USERS);
    static final String FIREBASE_URL_RIDES = FIREBASE_URL.concat("/").concat(FIREBASE_LOCATION_RIDES);

    static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    static final String KEY_GOOGLE_EMAIL = "g_mail";
    static final String KEY_PROVIDER = "provider";
    static final String KEY_ENCODED_EMAIL = "encemail";
    static final String KEY_NAME = "profile_name";

    static final String PROVIDER_DATA_DISPLAY_NAME = "disp_name";

    static final String GOOGLE_PROVIDER = "google";

}
