package me.yashtrivedi.ideal_succotash.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Parcel;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CancelRideIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS

    public CancelRideIntentService() {
        super("CancelRideIntentService");
    }

    NotificationManager notificationManager;
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            final String param1 = intent.getStringExtra("myEmail");
            final int param3 = intent.getIntExtra("notif",13123);
            final String paramTag = intent.getStringExtra("paramTag");
            //((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(paramTag,param3);

            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.cancel("onetwothree",12345);
            //notificationManager.cancelAll();

            final String param2 = intent.getStringExtra("requested");
            if (Constants.ACTION_CANCEL_RIDE.equals(action)) {
                handleActionCancel(param1, param2);
            } else if (Constants.ACTION_ACCEPT_RIDE.equals(action)) {
                handleActionAccept(param1, param2);
            } else if (Constants.ACTION_REJECT_RIDE.equals(action)) {
                handleActionReject(param1, param2);
            }
        }
    }



    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionCancel(String myEmail, String reqEmail) {
        // TODO: Handle action Foo
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(BaseApplication.utils.rollToEmail(reqEmail)).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE).concat("/").concat(myEmail));
        firebase.removeValue();
        Log.d("Cancel",firebase.toString());
        Firebase firebase1 = new Firebase(Constants.FIREBASE_URL_USER_REQUEST.concat("/").concat(myEmail).concat("/").concat(reqEmail));
        firebase1.removeValue();
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionAccept(final String myEmail, final String reqEmail) {
        // TODO: Handle action Baz
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(reqEmail).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE).concat("/").concat(myEmail));
        Map<String,Object> map = new HashMap<>();
        Log.d("mail",myEmail);
        map.put(Constants.REQUEST_STATUS,Constants.RIDE_REQUEST_ACCEPTED);
        firebase.updateChildren(map);
        Firebase firebase1 = new Firebase(Constants.FIREBASE_URL_USER_REQUEST.concat("/").concat(myEmail));
        firebase1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(String s : ((HashMap<String,Object>)dataSnapshot.getValue()).keySet()){
                    Log.d("Remove",s);
                    Log.d("Remove",reqEmail)
;                    if(!s.equals(reqEmail)){
                        Firebase firebase2 = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(s).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE).concat("/").concat(myEmail));
                        Log.d("remove",firebase2.toString());
                        firebase2.removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void handleActionReject(String myEmail, String reqEmail) {
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(reqEmail).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE).concat("/").concat(myEmail));
        firebase.removeValue();
        Firebase firebase1 = new Firebase(Constants.FIREBASE_URL_USER_REQUEST.concat("/").concat(myEmail).concat("/").concat(reqEmail));
        firebase1.removeValue();


    }
}
