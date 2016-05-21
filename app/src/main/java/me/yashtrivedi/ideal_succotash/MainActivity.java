package me.yashtrivedi.ideal_succotash;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Constants.KEY_OFFERED,false)){
            startService(new Intent(this,OfferService.class));
            fm.beginTransaction().add(R.id.container,new OfferedRideFragment()).commit();
        }else
            fm.beginTransaction().add(R.id.container,new ListFragment()).commit();

    }

    public void showRequestForm(View view){

        DialogFragment dialogFragment = ShowRequestFormFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "ShowRequestFormFragment");

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
}
