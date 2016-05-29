package me.yashtrivedi.ideal_succotash.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;

public class StartRideService extends Service {
    public StartRideService() {
    }
Bundle b;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        b = intent.getExtras();
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(BaseApplication.utils.rollToEmail(b.getString(Constants.KEY_ENCODED_EMAIL))).concat("/").concat(Constants.RIDE_STARTED));
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("value",dataSnapshot.getValue().toString());
                Log.d("key",dataSnapshot.getKey());
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
