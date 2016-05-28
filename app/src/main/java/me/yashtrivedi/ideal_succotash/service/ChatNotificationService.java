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

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.activity.ChatActivity;
import me.yashtrivedi.ideal_succotash.activity.MainActivity;
import me.yashtrivedi.ideal_succotash.fragment.ChatThreadFragment;
import me.yashtrivedi.ideal_succotash.model.Threads;

/**
 * Created by amit on 26-May-16.
 */

public class ChatNotificationService extends Service {

    Firebase firebase;
    static List<Threads> list;

    public ChatNotificationService() {

    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, int startId) {
        // Toast.makeText(getBaseContext(), "service started", Toast.LENGTH_LONG).show();
        //startForeground(123110, null);
        if(BaseApplication.utils.getMyEmail()==null){
            stopSelf();
        }
        list = new ArrayList<>();
        firebase = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(BaseApplication.utils.getMyEmail()).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS));

        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Threads t = dataSnapshot.getValue(Threads.class);
                t.setKey(dataSnapshot.getKey());

                if (t.getUnreadCount() > 0)
                    list.add(t);

                if (list.size() == 1) {
                    Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Intent intent2 = new Intent(getApplicationContext(), ChatActivity.class);
                    intent2.putExtra(Constants.THREAD_EMAIL, t.getEmail());
                    intent2.putExtra(Constants.CONVERSATION_PUSH_ID, t.getKey());
                    Log.d("sendPushID",t.getKey());
                    intent2.putExtra("toChat", true);
                    intent2.putExtra(Constants.USER_NAME, t.getName());
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 13110, intent2, 0);
                    NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(new long[0])
                            .setContentTitle(t.getName() + " (" + BaseApplication.utils.emailToroll(t.getEmail()) + ") ")
                            .setContentText(t.getmsg())
                            .setSound(notificationUri)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify("onethreetwo", 12345, notif.build());
                } else if(list.size()>1) {
                    String text = "";
                    for(Threads thread : list){
                        if(text.equals("")){
                            text+=thread.getName()+": "+thread.getmsg();
                        }
                        else{
                            text.concat("/n").concat(thread.getName()).concat(": ").concat(thread.getmsg());
                        }
                    }
                    Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Intent intent2 = new Intent(getApplicationContext(), ChatActivity.class);
                    intent2.putExtra(Constants.THREAD_EMAIL, t.getEmail());
                    intent2.putExtra(Constants.CONVERSATION_PUSH_ID, t.getKey());
                    intent2.putExtra("toChat", true);
                    intent2.putExtra(Constants.USER_NAME, t.getName());
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 13110, intent2, 0);
                    NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setVibrate(new long[0])
                            .setContentTitle("New Messages")
                            .setContentText(text)
                            .setSound(notificationUri)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify("onethreetwo", 12345, notif.build());
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Threads t = dataSnapshot.getValue(Threads.class);
                t.setKey(dataSnapshot.getKey());

                if (t.getUnreadCount() > 0){
                    for (Threads tr : list) {
                        if (tr.getKey().equals(t.getKey())) {
                            list.remove(tr);
                            list.add(BaseApplication.utils.getInsertPositionByTime(list,0,list.size()-1,t.getTime()),t);
                            break;
                        }
                    }
                    if (list.size() == 1) {
                        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Intent intent2 = new Intent(getApplicationContext(), ChatActivity.class);
                        intent2.putExtra(Constants.THREAD_EMAIL, t.getEmail());
                        intent2.putExtra(Constants.CONVERSATION_PUSH_ID, t.getKey());
                        intent2.putExtra("toChat", true);
                        intent2.putExtra(Constants.USER_NAME, t.getName());
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 13110, intent2, 0);
                        NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setVibrate(new long[0])
                                .setContentTitle(t.getName() + " (" + BaseApplication.utils.emailToroll(t.getEmail()) + ") ")
                                .setContentText(t.getmsg())
                                .setSound(notificationUri)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify("onethreetwo", 12345, notif.build());
                    } else if(list.size()>1) {
                        String text = "";
                        for(Threads thread : list){
                            if(text.equals("")){
                                text+=thread.getName()+": "+thread.getmsg();
                            }
                            else{
                                text.concat("/n").concat(thread.getName()).concat(": ").concat(thread.getmsg());
                            }
                        }
                        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Intent intent2 = new Intent(getApplicationContext(), ChatActivity.class);
                        intent2.putExtra(Constants.THREAD_EMAIL, t.getEmail());
                        intent2.putExtra(Constants.CONVERSATION_PUSH_ID, t.getKey());
                        intent2.putExtra("toChat", true);
                        intent2.putExtra(Constants.USER_NAME, t.getName());
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 13110, intent2, 0);
                        NotificationCompat.Builder notif = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setVibrate(new long[0])
                                .setContentTitle("New Messages")
                                .setContentText(text)
                                .setSound(notificationUri)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify("onethreetwo", 12345, notif.build());
                    }
                }else
                    for (Threads tr : list) {
                        if (tr.getKey().equals(t.getKey())) {
                            list.remove(tr);
                            break;
                        }
                    }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (Threads t : list) {
                    if (t.getKey().equals(dataSnapshot.getKey())) {
                        list.remove(t);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
