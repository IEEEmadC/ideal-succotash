package me.yashtrivedi.ideal_succotash;

/**
 * Created by yashtrivedi on 26/04/16.
 */
public class Utils {

    static String encodeEmail(String unencodedEmail){
        return unencodedEmail.replace(".",",");
    }

    static String emailToroll(String email){
        return email.split("@")[0].toUpperCase();
    }

    static String rollToEmail(String roll){
        return roll.concat("@nirmauni,ac,in").toLowerCase();
    }
}
