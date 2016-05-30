package me.yashtrivedi.ideal_succotash.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.collection.LLRBNode;

import net.frederico.showtipsview.ShowTipsBuilder;
import net.frederico.showtipsview.ShowTipsView;
import net.frederico.showtipsview.ShowTipsViewInterface;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.fragment.ListFragment;
import me.yashtrivedi.ideal_succotash.fragment.OfferedRideFragment;
import me.yashtrivedi.ideal_succotash.service.ChatNotificationService;


public class MainActivity extends AppCompatActivity {

    Boolean offered;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        offered = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Constants.KEY_OFFERED, false);
        if (offered) {
            fm.beginTransaction().replace(R.id.container, new OfferedRideFragment()).commit();
        } else {
            ListFragment f = new ListFragment();
            Bundle b = new Bundle();
            b.putBoolean("animation", false);
            f.setArguments(b);
            fm.beginTransaction().replace(R.id.container, f).commit();
        }

        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(BaseApplication.utils.getMyEmail()));
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null && offered) {
                    PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putBoolean(Constants.KEY_OFFERED, false).apply();
                    ListFragment f = new ListFragment();
                    Bundle b = new Bundle();
                    b.putBoolean("animation", false);
                    f.setArguments(b);
                    fm.beginTransaction().replace(R.id.container, f).commit();
                } else if (dataSnapshot.getValue() != null && !offered) {
                    PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putBoolean(Constants.KEY_OFFERED, true).apply();
                    fm.beginTransaction().replace(R.id.container, new OfferedRideFragment()).commit();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Intent serviceIntent = new Intent(MainActivity.this, ChatNotificationService.class);

        if(!isMyServiceRunning(ChatNotificationService.class))
            startService(serviceIntent);

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    View addingButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.chat_icon);

        ImageView chatIcon = new ImageView(this, null, android.R.attr.actionButtonStyle);
        chatIcon.setImageResource(R.drawable.ic_chat_white_24dp);
        chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ChatActivity.class));
            }
        });
        chatIcon.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (left == 0 && top == 0 && right == 0 && bottom == 0)
                    return;
                int[] location = new int[2];
                view.getLocationOnScreen(location);


            }
        });

        menuItem.setActionView(chatIcon);
        addingButton = menuItem.getActionView();

       if(!PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("runFirst",false)) {
           final ShowTipsView showTipsView = new ShowTipsBuilder(this)
                   .setTarget(addingButton)
                   .setTitle("Chat Button")
                   .setDescription("Chat with others on tapping this button")
                   .setDelay(100)
                   .build();
           showTipsView.setAlpha(0.9f);
           showTipsView.setButtonColor(Color.DKGRAY);
           showTipsView.show(this);
           showTipsView.setDisplayOneTime(true);

           PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putBoolean("runFirst",true).apply();
       }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
