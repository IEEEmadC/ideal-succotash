package me.yashtrivedi.ideal_succotash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.fragment.ListFragment;
import me.yashtrivedi.ideal_succotash.fragment.OfferedRideFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Constants.KEY_OFFERED, false)) {
            fm.beginTransaction().replace(R.id.container, new OfferedRideFragment()).commit();
        } else {
            ListFragment f = new ListFragment();
            Bundle b = new Bundle();
            b.putBoolean("animation",false);
            f.setArguments(b);
            fm.beginTransaction().replace(R.id.container, f).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this,ChatActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
