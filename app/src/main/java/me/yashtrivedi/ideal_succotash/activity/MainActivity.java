package me.yashtrivedi.ideal_succotash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.fragment.ListFragment;
import me.yashtrivedi.ideal_succotash.fragment.OfferedRideFragment;
import me.yashtrivedi.ideal_succotash.service.ChatNotificationService;
import tslamic.fancybg.FancyBackground;

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
            b.putBoolean("animation",false);
            f.setArguments(b);
            fm.beginTransaction().replace(R.id.container, f).commit();

        }

        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(BaseApplication.utils.getMyEmail()));
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null && offered){
                    PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putBoolean(Constants.KEY_OFFERED,false).apply();
                    ListFragment f = new ListFragment();
                    Bundle b = new Bundle();
                    b.putBoolean("animation",false);
                    f.setArguments(b);
                    fm.beginTransaction().replace(R.id.container, f).commit();
                } else if(dataSnapshot.getValue()!=null && !offered){
                    PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putBoolean(Constants.KEY_OFFERED,true).apply();
                    fm.beginTransaction().replace(R.id.container, new OfferedRideFragment()).commit();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Intent serviceIntent = new Intent(MainActivity.this, ChatNotificationService.class);
        startService(serviceIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this,ChatActivity.class));
        return true;
    }
}
