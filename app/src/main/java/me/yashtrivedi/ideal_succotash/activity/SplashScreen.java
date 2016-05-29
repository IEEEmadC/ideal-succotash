package me.yashtrivedi.ideal_succotash.activity;

import android.content.Intent;
import android.graphics.Paint;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;

/**
 * Created by amit on 28-May-16.
 */

public class SplashScreen extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {
        getSupportActionBar().hide();
        configSplash.setBackgroundColor(R.color.colorAccent);
        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP
        configSplash.setPathSplash(Constants.DROID_LOGO); //set path String
        configSplash.setOriginalHeight(390); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(310); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(2000);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.colorPrimaryDark); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(500);
        configSplash.setPathSplashFillColor(R.color.colorPrimary); //path object filling color
        configSplash.setTitleSplash("the carpooling app");
        configSplash.setAnimTitleDuration(500);
        configSplash.setTitleTextSize(40);
        configSplash.setTitleFont("fonts/GoodDog.otf");
    }

    @Override
    public void animationsFinished() {

       Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
