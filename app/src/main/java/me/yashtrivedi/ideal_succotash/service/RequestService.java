package me.yashtrivedi.ideal_succotash.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.activity.MainActivity;

public class RequestService extends Service {

    Bundle b;

    NotificationManager notificationManager;
    public RequestService() {
    }

    Firebase firebase;
    ChildEventListener listener;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        b = intent.getExtras();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),13123,i,0);
        Intent iCancel = new Intent(getApplicationContext(),CancelRideIntentService.class);
        iCancel.putExtra("myEmail",b.getString("myEmail"));
        iCancel.putExtra("requested",b.getString(Constants.KEY_ENCODED_EMAIL));
        iCancel.setAction(Constants.ACTION_CANCEL_RIDE);
        iCancel.putExtra("notif",13123);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Requesting Ride")
                .setVibrate(new long[0])
                .setContentIntent(pendingIntent)
                .addAction(new NotificationCompat.Action(R.drawable.ic_close_black_24dp, "Cancel", PendingIntent.getService(getApplicationContext(),13123,iCancel,0)));
        startForeground(13123, builder.build());

        firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(b.getString(Constants.REQUESTED_USER, "")));
        Log.d("firebase", firebase.toString());
        listener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int status = Integer.parseInt(dataSnapshot.getValue().toString());

                if (status == Constants.RIDE_REQUEST_ACCEPTED) {
                    Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(new long[0])
                            .setContentTitle(b.getString("name") + " (" + b.getString(Constants.KEY_ENCODED_EMAIL) + ")")
                            .setContentText(BaseApplication.utils.statusString(status) + " your request")
                            .setSubText("Car No: " + b.getString(Constants.CAR_NO))
                            .setSound(notificationUri)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
                    Intent i = new Intent(getApplicationContext(),StartRideService.class);
                    i.putExtras(b);
                    startService(i);
                    notificationManager.notify(12123, notif.build());
                } else if (status == Constants.RIDE_REQUEST_REJECTED) {
                    Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(new long[0])
                            .setContentTitle(b.getString("name") + " (" + b.getString(Constants.KEY_ENCODED_EMAIL) + ")")
                            .setContentText(BaseApplication.utils.statusString(Constants.RIDE_REQUEST_REJECTED) + " your request")
                            .setSound(notificationUri)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
                    notificationManager.notify(12123, notif.build());
                }
                stopForeground(true);
                firebase.removeEventListener(listener);
                stopSelf();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setVibrate(new long[0])
                        .setContentTitle(b.getString("name") + " (" + b.getString(Constants.KEY_ENCODED_EMAIL) + ")")
                        .setContentText("Cancelled the Ride");
                notificationManager.notify(12123, notif.build());
                stopForeground(true);
                firebase.removeEventListener(listener);
                stopSelf();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        firebase.addChildEventListener(listener);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
