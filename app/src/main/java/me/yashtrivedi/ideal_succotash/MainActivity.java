package me.yashtrivedi.ideal_succotash;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity implements ListFragment.Callbacks {

    private FloatingActionButton mfab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mfab = (FloatingActionButton)findViewById(R.id.fab);

        FragmentManager fm = getSupportFragmentManager();
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Constants.KEY_OFFERED, false)) {

            fm.beginTransaction().replace(R.id.container, new OfferedRideFragment()).commit();


        } else
            fm.beginTransaction().replace(R.id.container, new ListFragment()).commit();

        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRequestForm();
            }
        });

    }

    public void showRequestForm() {
        DialogFragment dialogFragment = ShowOfferFormFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "ShowOfferFormFragment");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateFab() {
        mfab.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_close_white_24dp));
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPrefrences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(sharedPrefrences.getString(Constants.KEY_ENCODED_EMAIL,"null")).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE));
                firebase.removeValue(); //remove the node from ride request as the user is not willing to go
                mfab.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_add_white_24dp));
               /* mfab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showRequestForm();
                    }
                });
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new ListFragment()).commit();*/
            }
        });

    }


}
