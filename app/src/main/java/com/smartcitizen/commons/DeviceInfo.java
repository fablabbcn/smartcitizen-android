package com.smartcitizen.commons;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.smartcitizen.R;

/**
 * Created by ferran on 08/07/15.
 */
public class DeviceInfo {

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return TextHelper.capitalize(model);
        } else {
            return TextHelper.capitalize(manufacturer) + " " + model;
        }
    }

    public static String getAppVersion(Context ctx) {
        String ret = ctx.getString(R.string.app_name) + " version ";
        PackageInfo pInfo = null;
        try {
            pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            ret = ret + pInfo.versionName + " (" + pInfo.versionCode + ")";

        } catch (PackageManager.NameNotFoundException e) {
            ret = ret + " not available";
        }

        return ret;
    }

    public static String getAndroidVersion() {
        return Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")";
    }
}
