package me.yashtrivedi.ideal_succotash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import me.yashtrivedi.ideal_succotash.service.ChatNotificationService;

/**
 * Created by amit on 26-May-16.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ChatNotificationService.class);
        context.startService(serviceIntent);
        Log.d("service","started");
    }
}
