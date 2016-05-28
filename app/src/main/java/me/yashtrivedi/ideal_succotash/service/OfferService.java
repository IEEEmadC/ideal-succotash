package me.yashtrivedi.ideal_succotash.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.activity.MainActivity;
import me.yashtrivedi.ideal_succotash.model.RideRequest;

public class OfferService extends Service {
    List<RideRequest> rides;

    public OfferService() {
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        rides = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        rides = new ArrayList<>();
        final Firebase rideReqs = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.KEY_ENCODED_EMAIL, "")).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE));
        rideReqs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RideRequest request = dataSnapshot.getValue(RideRequest.class);
                request.setEmail(dataSnapshot.getKey());
                String paramTag = request.getEmail();
                if (request.getStatus() == Constants.RIDE_REQUEST_WAITING) {
                    rides.add(0, request);
                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),13123,intent,0);
                    Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Ride Request")
                            .setContentIntent(pendingIntent)
                            .setSound(notificationUri)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(new long[0]);
                    if (rides.size() == 1) {
                        Intent aIntent = new Intent(getApplicationContext(),CancelRideIntentService.class);
                        aIntent.setAction(Constants.ACTION_ACCEPT_RIDE);
                        aIntent.putExtra("myEmail",request.getEmail());
                        aIntent.putExtra("notif",13123);
                        aIntent.putExtra("paramTag",paramTag);
                        aIntent.putExtra("requested",PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.KEY_ENCODED_EMAIL, ""));
                        PendingIntent aPendingIntent = PendingIntent.getService(getApplicationContext(),13123,aIntent,0);
                        Intent rIntent = new Intent(getApplicationContext(),CancelRideIntentService.class);
                        rIntent.setAction(Constants.ACTION_ACCEPT_RIDE);
                        rIntent.putExtra("notif",13123);
                        rIntent.putExtra("paramTag",paramTag);
                        rIntent.putExtra("myEmail",request.getEmail());
                        rIntent.putExtra("requested",PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.KEY_ENCODED_EMAIL, ""));
                        PendingIntent rPendingIntent = PendingIntent.getService(getApplicationContext(),13123,rIntent,0);
                        Boolean toNirma = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(Constants.TO_NIRMA, false);
                        builder.setContentText(request.getuserName() + " (" + BaseApplication.utils.emailToroll(request.getEmail()) + ") is willing to ride with you" + (toNirma ? "from " : "to ") + request.getArea())
                                .addAction(new NotificationCompat.Action(R.drawable.ic_close_black_24dp, "Reject", rPendingIntent))
                                .addAction(new NotificationCompat.Action(R.drawable.ic_done_black_24dp, "Accept", aPendingIntent));
                    } else {
                        String text = "";
                        for (RideRequest rideRequest : rides) {
                            if (text.equals(""))
                                text += rideRequest.getuserName() + " (" + BaseApplication.utils.emailToroll(rideRequest.getEmail()) + ")";
                            else
                                text += "\n" + rideRequest.getuserName() + " (" + BaseApplication.utils.emailToroll(rideRequest.getEmail()) + ")";
                        }
                        builder.setContentText(rides.size() + " Requests")
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(text));
                    }

                    notificationManager.notify("onetwothree",12345, builder.build());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                RideRequest request = dataSnapshot.getValue(RideRequest.class);
                if (request.getStatus() != 0) {
                    for (RideRequest r : rides) {
                        if (r.getEmail().equals(request.getEmail())) {
                            rides.remove(r);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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
