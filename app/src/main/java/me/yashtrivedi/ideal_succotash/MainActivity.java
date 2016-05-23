package me.yashtrivedi.ideal_succotash;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

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
}
