package ksmaragh.c4q.nyc.accessrobot;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Allison on 8/30/15.
 */
public class SharedPreferencesHelper {
    Context mContext;

    public SharedPreferencesHelper(Context context) {
        mContext = context;
    }

    public void saveInterfaceType(int interfaceType) {
        SharedPreferences.Editor editor =
                mContext.getSharedPreferences(AppConstants.SETTINGS_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(AppConstants.SETTINGS_PREFERENCES_NAME, interfaceType);
        editor.commit();

        int test = getInterfaceType();
    }

    public int getInterfaceType() {
        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences(AppConstants.SETTINGS_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(AppConstants.SETTINGS_PREFERENCES_NAME, AppConstants.INTERFACE_BLUETOOTH);
    }
}
