package me.yashtrivedi.ideal_succotash;

/**
 * Created by yashtrivedi on 26/04/16.
 */
public class Utils {

    static String encodeEmail(String unencodedEmail){
        return unencodedEmail.replace(".",",");
    }
}
