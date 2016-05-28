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
    public static final String FIREBASE_LOCATION_MESSAGES = "messages";
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
    //splash svg
    public static final String DROID_LOGO = "M 357.07,133.90\n" +
            "           C 352.83,140.62 335.60,143.91 328.00,144.00\n" +
            "             312.49,144.18 298.28,141.85 287.09,129.99\n" +
            "             277.01,119.29 273.17,101.34 273.00,87.00\n" +
            "             272.77,66.11 279.12,42.90 298.00,31.46\n" +
            "             311.17,23.49 327.33,22.24 342.00,26.15\n" +
            "             352.11,28.85 357.95,30.67 358.00,42.00\n" +
            "             358.01,44.45 358.59,50.58 355.69,51.56\n" +
            "             353.28,52.37 348.25,48.52 346.00,47.32\n" +
            "             340.09,44.18 333.73,42.42 327.00,42.75\n" +
            "             293.50,44.41 289.15,100.63 310.01,118.79\n" +
            "             318.54,126.21 331.75,126.81 342.00,122.94\n" +
            "             347.55,120.84 352.17,117.18 358.00,116.00\n" +
            "             358.94,121.25 360.07,129.14 357.07,133.90 Z\n" +
            "           M 49.00,91.00\n" +
            "           C 42.42,91.13 41.13,92.42 41.00,99.00\n" +
            "             41.00,99.00 41.00,133.00 41.00,133.00\n" +
            "             41.00,135.07 41.23,138.74 39.98,140.44\n" +
            "             37.37,143.97 22.63,143.97 20.02,140.44\n" +
            "             18.77,138.74 19.00,135.07 19.00,133.00\n" +
            "             19.00,133.00 19.00,35.00 19.00,35.00\n" +
            "             19.00,32.99 18.76,29.21 20.02,27.59\n" +
            "             22.56,24.32 37.44,24.32 39.98,27.59\n" +
            "             41.24,29.21 41.00,32.99 41.00,35.00\n" +
            "             41.00,35.00 41.00,63.00 41.00,63.00\n" +
            "             41.01,65.54 40.68,69.48 42.60,71.40\n" +
            "             44.52,73.32 48.46,72.99 51.00,73.00\n" +
            "             51.00,73.00 78.00,73.00 78.00,73.00\n" +
            "             80.35,72.98 83.61,73.19 85.40,71.40\n" +
            "             87.32,69.48 86.99,65.54 87.00,63.00\n" +
            "             87.00,63.00 87.00,38.00 87.00,38.00\n" +
            "             87.00,35.61 86.57,29.52 87.99,27.73\n" +
            "             89.49,25.84 94.68,25.23 97.00,25.09\n" +
            "             106.55,24.54 108.98,25.82 109.00,35.00\n" +
            "             109.00,35.00 109.00,133.00 109.00,133.00\n" +
            "             109.00,135.07 109.23,138.74 107.98,140.44\n" +
            "             105.37,143.96 90.63,143.96 88.02,140.44\n" +
            "             86.77,138.74 87.00,135.07 87.00,133.00\n" +
            "             87.00,133.00 87.00,91.00 87.00,91.00\n" +
            "             87.00,91.00 49.00,91.00 49.00,91.00 Z\n" +
            "           M 157.98,27.59\n" +
            "           C 159.24,29.21 159.00,32.99 159.00,35.00\n" +
            "             159.00,35.00 159.00,133.00 159.00,133.00\n" +
            "             159.00,135.07 159.23,138.74 157.98,140.44\n" +
            "             155.37,143.96 140.63,143.96 138.02,140.44\n" +
            "             136.77,138.74 137.00,135.07 137.00,133.00\n" +
            "             137.00,133.00 137.00,51.00 137.00,51.00\n" +
            "             137.00,51.00 137.00,34.00 137.00,34.00\n" +
            "             137.01,32.16 136.85,29.10 138.02,27.59\n" +
            "             139.27,25.99 142.13,25.76 144.00,25.43\n" +
            "             147.27,25.03 155.77,24.75 157.98,27.59 Z\n" +
            "           M 408.00,91.00\n" +
            "           C 401.72,91.12 400.12,92.72 400.00,99.00\n" +
            "             400.00,99.00 400.00,133.00 400.00,133.00\n" +
            "             400.00,135.07 400.23,138.74 398.98,140.44\n" +
            "             396.37,143.96 381.63,143.96 379.02,140.44\n" +
            "             377.77,138.74 378.00,135.07 378.00,133.00\n" +
            "             378.00,133.00 378.00,35.00 378.00,35.00\n" +
            "             378.00,32.99 377.76,29.21 379.02,27.59\n" +
            "             381.56,24.32 396.44,24.32 398.98,27.59\n" +
            "             400.24,29.21 400.00,32.99 400.00,35.00\n" +
            "             400.00,35.00 400.00,63.00 400.00,63.00\n" +
            "             400.01,65.54 399.68,69.48 401.60,71.40\n" +
            "             403.52,73.32 407.46,72.99 410.00,73.00\n" +
            "             410.00,73.00 437.00,73.00 437.00,73.00\n" +
            "             439.35,72.98 442.61,73.19 444.40,71.40\n" +
            "             446.32,69.48 445.99,65.54 446.00,63.00\n" +
            "             446.00,63.00 446.00,38.00 446.00,38.00\n" +
            "             446.00,35.56 445.60,29.43 447.02,27.59\n" +
            "             448.46,25.74 453.74,25.22 456.00,25.09\n" +
            "             465.55,24.55 467.98,25.81 468.00,35.00\n" +
            "             468.00,35.00 468.00,133.00 468.00,133.00\n" +
            "             468.00,135.07 468.23,138.74 466.98,140.44\n" +
            "             464.37,143.97 449.63,143.97 447.02,140.44\n" +
            "             445.77,138.74 446.00,135.07 446.00,133.00\n" +
            "             446.00,133.00 446.00,91.00 446.00,91.00\n" +
            "             446.00,91.00 408.00,91.00 408.00,91.00 Z\n" +
            "           M 243.00,26.00\n" +
            "           C 256.95,26.00 262.99,23.80 263.81,33.00\n" +
            "             264.44,39.93 263.32,43.86 256.00,44.00\n" +
            "             256.00,44.00 238.00,44.00 238.00,44.00\n" +
            "             235.86,44.04 233.23,43.96 231.60,45.60\n" +
            "             229.68,47.52 230.01,51.46 230.00,54.00\n" +
            "             230.00,54.00 230.00,142.00 230.00,142.00\n" +
            "             225.35,142.61 212.17,144.70 209.02,140.44\n" +
            "             207.77,138.74 208.00,135.07 208.00,133.00\n" +
            "             208.00,133.00 208.00,44.00 208.00,44.00\n" +
            "             208.00,44.00 184.00,44.00 184.00,44.00\n" +
            "             181.68,43.98 178.58,44.24 176.85,42.40\n" +
            "             173.76,39.11 175.27,30.08 176.00,26.00\n" +
            "             176.00,26.00 243.00,26.00 243.00,26.00 Z";

}
