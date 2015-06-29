package cat.lafosca.smartcitizen.commons;

import android.app.Application;

import cat.lafosca.smartcitizen.managers.SharedPreferencesManager;

/**
 * Created by ferran on 04/06/15.
 */
public class SmartCitizenApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferencesManager.getInstance().init(getApplicationContext());
    }
}
