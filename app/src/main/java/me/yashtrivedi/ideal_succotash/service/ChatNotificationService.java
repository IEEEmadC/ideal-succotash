package me.yashtrivedi.ideal_succotash.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.activity.ChatActivity;
import me.yashtrivedi.ideal_succotash.activity.MainActivity;
import me.yashtrivedi.ideal_succotash.fragment.ChatThreadFragment;

/**
 * Created by amit on 26-May-16.
 */

public class ChatNotificationService extends Service {

    Firebase firebase;
    String chatId;

    public ChatNotificationService() {

    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, int startId) {
        // Toast.makeText(getBaseContext(), "service started", Toast.LENGTH_LONG).show();
        firebase = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(Utils.getMyEmail(getBaseContext())).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS));

        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                chatId = dataSnapshot.getKey();
                Firebase firebaseChat = new Firebase(Constants.FIREBASE_URL.concat("/").concat(Constants.FIREBASE_LOCATION_CHATS).concat("/").concat(chatId).concat("/").concat(Constants.FIREBASE_LOCATION_MESSAGES));
                Query query = firebaseChat.orderByChild("time").limitToLast(1);
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String values[] = dataSnapshot.getValue().toString().split(",");
                        String emailValues[] = values[2].split("=");
                        String emailTemp = emailValues[1] + "," + values[3] + "," + values[4];
                        String emailTemp2 = emailValues[1] + "." + values[3] + "." + values[4];
                        String emailTe2[] = emailTemp2.split(Pattern.quote("}"));
                        String email = emailTemp.split(Pattern.quote("}"))[0];
                        String message = values[1].split("=")[1];


                        if (!(email.equals(Utils.getMyEmail(getBaseContext())))) {

                            Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Intent intent2 = new Intent(getApplicationContext(), ChatActivity.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 13110, intent2, 0);
                            NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setVibrate(new long[0])
                                    .setContentTitle(emailTe2[0])
                                    .setContentText(message)
                                    .setSound(notificationUri)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true);
                            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify("onethreetwo", 12345, notif.build());

                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Log.d("childChanged1231", dataSnapshot.getKey());
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
