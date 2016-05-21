package me.yashtrivedi.ideal_succotash;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Firebase rideReqs = new Firebase(Constants.FIREBASE_URL_REQUEST_RIDE.concat("/").concat(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.KEY_ENCODED_EMAIL,"")));
        rideReqs.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RideRequest request = dataSnapshot.getValue(RideRequest.class);
                request.setEmail(dataSnapshot.getKey());
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                PendingIntent pendingIntent;
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Ride Request")
                        .setContentText(request.getName() + " (" + Utils.emailToroll(request.getEmail()) + ") is willing to ride with you")
//                        .setOngoing(true)
                        .addAction(new NotificationCompat.Action(R.drawable.ic_close_black_24dp,"Reject",null))
                        .addAction(new NotificationCompat.Action(R.drawable.ic_done_black_24dp,"Accept",null));
                notificationManager.notify(12345,builder.build());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
