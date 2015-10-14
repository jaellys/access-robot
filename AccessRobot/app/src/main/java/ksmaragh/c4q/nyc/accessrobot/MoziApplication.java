package ksmaragh.c4q.nyc.accessrobot;

/**
 * Created by Ramona Harrison
 * on 9/1/15.
 */

import android.app.Application;
import android.content.Context;

import com.parse.Parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MoziApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Properties configFile = new Properties();
        try {
            InputStream is = getResources().getAssets().open("config.properties", Context.MODE_PRIVATE);
            configFile.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String appKey = configFile.getProperty("appKey");
        String mobileKey = configFile.getProperty("mobileKey");

        // *********************** PARSE API KEYS HERE **********************************************************************

        Parse.initialize(this, appKey, mobileKey);

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Lekton-Bold.ttf");
    }
}