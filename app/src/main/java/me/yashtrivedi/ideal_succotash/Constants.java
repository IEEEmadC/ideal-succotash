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
    public static final String RIDE_STARTED = "started";

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
    public static final String DROID_LOGO = "d=\"M 186.00,10.12\n" +
            "           C 227.88,19.77 261.33,50.11 274.67,91.00\n" +
            "             274.67,91.00 278.54,104.00 278.54,104.00\n" +
            "             278.54,104.00 280.09,119.00 280.09,119.00\n" +
            "             280.09,119.00 280.99,129.00 280.99,129.00\n" +
            "             280.99,129.00 280.08,139.00 280.08,139.00\n" +
            "             280.08,139.00 278.33,155.00 278.33,155.00\n" +
            "             269.77,195.25 238.53,229.89 200.00,243.76\n" +
            "             182.88,249.92 167.97,251.21 150.00,251.00\n" +
            "             140.85,250.89 131.77,248.87 123.00,246.42\n" +
            "             71.06,231.97 33.64,185.21 33.00,131.00\n" +
            "             32.83,116.46 35.62,97.40 41.34,84.00\n" +
            "             55.99,49.67 82.31,24.32 118.00,12.67\n" +
            "             123.71,10.81 136.97,8.03 143.00,7.29\n" +
            "             143.00,7.29 153.00,6.42 153.00,6.42\n" +
            "             161.19,5.45 177.68,8.20 186.00,10.12 Z\n" +
            "           M 122.00,30.52\n" +
            "           C 91.10,40.70 67.44,65.61 56.92,96.00\n" +
            "             53.55,105.74 52.02,110.46 52.00,121.00\n" +
            "             51.97,142.31 51.09,149.35 59.45,170.00\n" +
            "             73.36,204.35 111.48,232.56 149.00,233.00\n" +
            "             173.89,233.29 188.70,231.88 211.00,219.13\n" +
            "             222.27,212.69 231.91,204.05 240.07,194.00\n" +
            "             247.35,185.03 252.68,174.81 256.59,164.00\n" +
            "             262.10,148.75 262.18,138.89 262.00,123.00\n" +
            "             261.87,112.59 259.75,103.75 256.34,94.00\n" +
            "             240.32,48.14 193.70,20.83 146.00,25.29\n" +
            "             138.60,26.42 129.06,28.19 122.00,30.52 Z\n" +
            "           M 92.87,106.90\n" +
            "           C 92.77,108.35 92.50,108.74 92.00,110.00\n" +
            "             92.00,110.00 92.00,103.00 92.00,103.00\n" +
            "             92.00,103.00 88.00,104.00 88.00,104.00\n" +
            "             88.00,104.00 90.64,106.27 90.64,106.27\n" +
            "             90.64,106.27 89.83,113.07 89.83,113.07\n" +
            "             89.83,113.07 89.00,125.00 89.00,125.00\n" +
            "             89.00,125.00 87.00,122.00 87.00,122.00\n" +
            "             87.00,122.00 88.59,139.71 88.59,139.71\n" +
            "             88.59,139.71 91.00,142.00 91.00,142.00\n" +
            "             88.40,132.83 91.33,122.28 92.00,113.00\n" +
            "             92.00,113.00 103.00,113.00 103.00,113.00\n" +
            "             103.00,113.00 101.00,115.00 101.00,115.00\n" +
            "             101.00,115.00 103.00,117.00 103.00,117.00\n" +
            "             103.00,117.00 91.79,116.00 91.79,116.00\n" +
            "             91.79,116.00 91.79,137.91 91.79,137.91\n" +
            "             91.79,137.91 95.00,143.00 95.00,143.00\n" +
            "             95.00,143.00 88.00,144.00 88.00,144.00\n" +
            "             88.00,144.00 86.00,123.00 86.00,123.00\n" +
            "             86.00,123.00 79.00,122.00 79.00,122.00\n" +
            "             80.34,114.51 78.73,108.52 86.00,105.00\n" +
            "             86.00,105.00 81.00,104.00 81.00,104.00\n" +
            "             85.52,103.41 93.48,98.63 92.87,106.90 Z\n" +
            "           M 201.00,107.00\n" +
            "           C 205.28,107.00 209.97,106.40 214.00,108.00\n" +
            "             214.00,108.00 213.00,109.99 213.00,109.99\n" +
            "             213.00,109.99 190.00,109.99 190.00,109.99\n" +
            "             190.00,109.99 180.00,109.18 180.00,109.18\n" +
            "             180.00,109.18 169.00,114.00 169.00,114.00\n" +
            "             169.00,114.00 172.00,110.00 172.00,110.00\n" +
            "             181.41,105.59 190.79,107.00 201.00,107.00 Z\n" +
            "           M 231.00,122.52\n" +
            "           C 234.54,124.01 239.71,124.30 241.91,127.30\n" +
            "             246.12,133.04 243.81,143.70 237.00,146.00\n" +
            "             233.71,128.75 212.88,128.43 211.00,146.00\n" +
            "             211.00,146.00 171.00,146.00 171.00,146.00\n" +
            "             169.76,128.76 146.98,128.89 145.00,146.00\n" +
            "             142.21,146.00 136.51,146.38 134.23,144.98\n" +
            "             129.18,141.90 131.29,134.01 135.22,130.97\n" +
            "             141.14,126.38 153.58,125.22 156.00,124.27\n" +
            "             160.60,122.46 161.71,118.97 168.00,116.00\n" +
            "             166.79,118.18 163.09,123.04 164.82,125.40\n" +
            "             166.58,127.80 176.96,127.00 180.00,127.00\n" +
            "             180.00,127.00 225.00,127.00 225.00,127.00\n" +
            "             225.00,127.00 221.00,113.00 221.00,113.00\n" +
            "             223.97,117.90 225.34,120.14 231.00,122.52 Z\n" +
            "           M 185.00,115.00\n" +
            "           C 185.00,115.00 184.00,116.00 184.00,116.00\n" +
            "             184.00,116.00 184.00,115.00 184.00,115.00\n" +
            "             184.00,115.00 185.00,115.00 185.00,115.00 Z\n" +
            "           M 205.00,115.00\n" +
            "           C 205.00,115.00 204.00,116.00 204.00,116.00\n" +
            "             204.00,116.00 204.00,115.00 204.00,115.00\n" +
            "             204.00,115.00 205.00,115.00 205.00,115.00 Z\n" +
            "           M 215.00,115.00\n" +
            "           C 215.00,115.00 215.00,120.00 215.00,120.00\n" +
            "             215.00,120.00 213.00,120.00 213.00,120.00\n" +
            "             213.00,120.00 212.00,115.00 212.00,115.00\n" +
            "             212.00,115.00 215.00,115.00 215.00,115.00 Z\n" +
            "           M 80.00,120.00\n" +
            "           C 80.00,120.00 81.00,121.00 81.00,121.00\n" +
            "             81.00,121.00 81.00,120.00 81.00,120.00\n" +
            "             81.00,120.00 80.00,120.00 80.00,120.00 Z\n" +
            "           M 81.00,122.00\n" +
            "           C 81.00,122.00 87.00,121.00 87.00,121.00\n" +
            "             87.00,121.00 81.00,122.00 81.00,122.00 Z\n" +
            "           M 175.00,121.00\n" +
            "           C 175.00,121.00 175.00,125.00 175.00,125.00\n" +
            "             175.00,125.00 173.00,125.00 173.00,125.00\n" +
            "             173.00,125.00 173.00,121.00 173.00,121.00\n" +
            "             173.00,121.00 175.00,121.00 175.00,121.00 Z\n" +
            "           M 184.00,123.00\n" +
            "           C 184.00,123.00 187.00,121.00 187.00,121.00\n" +
            "             186.96,123.46 186.18,123.05 184.00,123.00 Z\n" +
            "           M 209.00,125.00\n" +
            "           C 209.00,125.00 204.00,124.00 204.00,124.00\n" +
            "             204.00,124.00 209.00,125.00 209.00,125.00 Z\n" +
            "           M 163.57,140.89\n" +
            "           C 166.94,143.76 166.86,150.42 160.96,152.74\n" +
            "             153.29,155.74 145.56,145.41 155.05,139.74\n" +
            "             158.10,139.02 160.94,138.66 163.57,140.89 Z\n" +
            "           M 227.89,140.02\n" +
            "           C 233.35,143.08 232.32,150.15 226.96,152.65\n" +
            "             220.61,155.60 210.91,147.16 220.06,140.02\n" +
            "             222.77,139.10 225.17,138.50 227.89,140.02 Z\n" +
            "           M 146.00,262.47\n" +
            "           C 149.25,264.61 158.25,273.96 161.00,273.96\n" +
            "             164.04,273.96 172.28,264.72 175.42,262.42\n" +
            "             178.99,259.82 191.25,257.67 196.00,257.00\n" +
            "             189.31,266.35 179.16,273.84 171.00,282.00\n" +
            "             168.74,284.26 163.17,290.62 160.00,290.66\n" +
            "             156.76,290.71 151.40,284.32 149.00,282.09\n" +
            "             142.18,275.74 127.87,263.80 123.00,257.00\n" +
            "             127.82,257.41 142.29,260.04 146.00,262.47 Z";

}
