package me.yashtrivedi.ideal_succotash;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
