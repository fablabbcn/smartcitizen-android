package cat.lafosca.smartcitizen.commons;

import android.app.Application;

import cat.lafosca.smartcitizen.managers.SharedPreferencesManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by ferran on 04/06/15.
 */
public class SmartCitizenApp extends Application {

    private static SmartCitizenApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        instance = this;

        CountyCode.getInstance();//init

        SharedPreferencesManager.getInstance().init(getApplicationContext());
    }

    public static SmartCitizenApp getInstance() {
        return instance;
    }
}
