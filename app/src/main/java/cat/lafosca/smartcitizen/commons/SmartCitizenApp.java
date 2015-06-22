package cat.lafosca.smartcitizen.commons;

import android.app.Application;

import cat.lafosca.smartcitizen.controllers.SharedPreferencesController;
import cat.lafosca.smartcitizen.rest.RestController;

/**
 * Created by ferran on 04/06/15.
 */
public class SmartCitizenApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferencesController.getInstance().init(getApplicationContext());
    }
}
