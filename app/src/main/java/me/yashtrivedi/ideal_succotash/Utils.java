package me.yashtrivedi.ideal_succotash;

/**
 * Created by yashtrivedi on 26/04/16.
 */
public class Utils {

    public static String encodeEmail(String unencodedEmail) {
        return unencodedEmail.replace(".", ",");
    }

    public static String emailToroll(String email) {
        return email.split("@")[0].toUpperCase();
    }

    public static String rollToEmail(String roll) {
        return roll.concat("@nirmauni,ac,in").toLowerCase();
    }

    public static String statusString(int status) {
        switch (status) {
            case Constants.RIDE_REQUEST_ACCEPTED:
                return "Accepted";
            case Constants.RIDE_REQUEST_REJECTED:
                return "Rejected";
            case Constants.RIDE_REQUEST_WAITING:
                return "Waiting";
        }
        return "";
    }
}
