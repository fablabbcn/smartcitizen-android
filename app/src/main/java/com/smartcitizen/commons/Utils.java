package com.smartcitizen.commons;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;

/**
 * Created by ferran on 04/06/15.
 */
public class Utils {

    public static Drawable getDrawable(Context context, int resourceId) {

        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(resourceId);

        } else {
            drawable = context.getResources().getDrawable(resourceId);
        }

        return drawable;
    }

    public static boolean isGPSEnabled(Context context) {

        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled;
        //boolean network_enabled = false;

        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return gps_enabled;

    }

}
