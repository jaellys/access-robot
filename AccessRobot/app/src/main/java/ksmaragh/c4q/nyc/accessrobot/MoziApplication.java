package ksmaragh.c4q.nyc.accessrobot;

/**
 * Created by Ramona Harrison
 * on 9/1/15.
 */

import android.app.Application;

import com.parse.Parse;

public class MoziApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "2RYYOW1MtNULciwQYQoYxfiGX3f9lujssswVpbCV", "bTey4stJbGMhcN0raZaY0AdkqoJhxZcR4gpVkcBn");

        FontsOverride.setDefaultFont(this, "MONOSPACE", "Lekton-Bold.ttf");
    }
}