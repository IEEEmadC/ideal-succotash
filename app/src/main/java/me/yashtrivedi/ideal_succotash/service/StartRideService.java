package me.yashtrivedi.ideal_succotash.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;

public class StartRideService extends Service {
    Bundle b;

    public StartRideService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        b = intent.getExtras();
        Toast.makeText(StartRideService.this, "Service", Toast.LENGTH_SHORT).show();
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(BaseApplication.utils.rollToEmail(b.getString(Constants.KEY_ENCODED_EMAIL))));
        Log.d("hi", firebase.toString());
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals(Constants.RIDE_STARTED))
                    if (dataSnapshot.getValue().toString().equals("true")) {
                        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                        builder.setPriority(Notification.PRIORITY_HIGH)
                                .setContentTitle(b.getString("name") + " is leaving now")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setVibrate(new long[0])
                                .setSound(notificationUri)
                                .setAutoCancel(true);
                        notificationManager.notify(113113, builder.build());
                        stopSelf();
                    }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                stopSelf();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
