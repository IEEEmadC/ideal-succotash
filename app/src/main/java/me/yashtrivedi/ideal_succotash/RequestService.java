package me.yashtrivedi.ideal_succotash;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RequestService extends Service {
    public RequestService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
