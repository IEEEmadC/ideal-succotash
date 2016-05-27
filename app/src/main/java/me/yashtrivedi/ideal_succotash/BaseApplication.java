package me.yashtrivedi.ideal_succotash;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class BaseApplication extends Application {

    public static Utils utils;
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        utils = new Utils(getApplicationContext());
    }


}
