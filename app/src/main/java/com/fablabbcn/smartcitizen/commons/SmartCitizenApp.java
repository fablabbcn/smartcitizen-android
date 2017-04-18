package com.fablabbcn.smartcitizen.commons;

import android.app.Application;

import com.fablabbcn.smartcitizen.managers.SharedPreferencesManager;
import com.crashlytics.android.Crashlytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ferran on 04/06/15.
 */
public class SmartCitizenApp extends Application {

    private static SmartCitizenApp instance;

    private static MixpanelAPI mixpanelInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        instance = this;

        CountryCode.getInstance();//init

        SharedPreferencesManager.getInstance().init(getApplicationContext());
    }

    public static SmartCitizenApp getInstance() {
        return instance;
    }

    public MixpanelAPI getMixpanelInstance() {
        return mixpanelInstance;
    }

    public void setMixpanelInstance(MixpanelAPI mixpanelAPI) {
        mixpanelInstance = mixpanelAPI;
    }
}
