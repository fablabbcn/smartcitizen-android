package cat.lafosca.smartcitizen.commons;

import android.app.Application;

import cat.lafosca.smartcitizen.managers.SharedPreferencesManager;

/**
 * Created by ferran on 04/06/15.
 */
public class SmartCitizenApp extends Application {

    private static SmartCitizenApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        SharedPreferencesManager.getInstance().init(getApplicationContext());
    }

    public static SmartCitizenApp getInstance() {
        return instance;
    }
}
