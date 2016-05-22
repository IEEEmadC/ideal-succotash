package me.yashtrivedi.ideal_succotash;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class RequestService extends Service {

    Callbacks activity;
    int position;
    public RequestService() {
    }
    public void registerClient(Fragment fragment){
        activity = (Callbacks) fragment;
    }
    public class LocalBinder extends Binder {
        public RequestService getServiceInstance(){
            return RequestService.this;
        }
    }
    private final IBinder mBinder = new LocalBinder();



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Requesting Ride")
//                        .setOngoing(true)
                .addAction(new NotificationCompat.Action(R.drawable.ic_close_black_24dp,"Cancel",null));
        startForeground(13123,builder.build());
        Bundle b = intent.getExtras();
        position = b.getInt("position");
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(b.getString(Constants.REQUESTED_USER,"")));
        Log.d("firebase", firebase.toString());
        firebase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("return",dataSnapshot.getValue().toString());
                activity.update(Integer.parseInt(dataSnapshot.getValue().toString()),position);
                stopForeground(true);
                stopSelf();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                activity.remove(position);
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
        return mBinder;
    }

    interface Callbacks{
        void update(int status, int position);
        void remove(int position);
    }
}
