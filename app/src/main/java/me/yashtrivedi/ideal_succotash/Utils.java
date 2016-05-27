package me.yashtrivedi.ideal_succotash;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.List;

import me.yashtrivedi.ideal_succotash.model.Threads;

/**
 * Created by yashtrivedi on 26/04/16.
 */
public class Utils {

    Context context;
    public Utils(Context context){
        this.context = context;
    }
    public String encodeEmail(String unencodedEmail) {
        return unencodedEmail.replace(".", ",");
    }

    public String decodeEmail(String encodedEmail) {
        return encodedEmail.replace(",", ".");
    }

    public String emailToroll(String email) {
        return email.split("@")[0].toUpperCase();
    }

    public String rollToEmail(String roll) {
        return roll.concat("@nirmauni,ac,in").toLowerCase();
    }

    public String getMyEmail(){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.KEY_ENCODED_EMAIL,"");
    }

    public String getMyName(){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.KEY_NAME,"");
    }

    public int getInsertPositionByTime(List<Threads> list, int start, int end, long time){
        if(list.size()==0)
            return 0;
        Long latest = list.get(start).getTime();
        Long oldest = list.get(end).getTime();
        if(time < oldest) {
            return end + 1;
        } else if(time == oldest){
            return end;
        } else if(time >= latest){
            return start;
        } else {
            if (time > list.get((start + end) / 2).getTime()) {
                return getInsertPositionByTime(list, start, (start + end) / 2 - 1, time);
            } else if (time < list.get((start + end) / 2).getTime()) {
                return getInsertPositionByTime(list, (start + end) / 2 + 1, end, time);
            } else {
                return start;
            }
        }
    }

    public String statusString(int status) {
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
