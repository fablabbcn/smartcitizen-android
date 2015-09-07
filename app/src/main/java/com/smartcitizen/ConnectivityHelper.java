package com.smartcitizen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityHelper {

    /*public static void notifyUserAboutNoInternetConnectivity(Context context) {
        notifyUserAboutGenericError(context, R.string.no_internet_connection_label);
    }

    public static void notifyUserAboutAPIError(Context context) {
        notifyUserAboutGenericError(context, R.string.api_failure_label);
    }

    public static void notifyUserAboutGenericError(Context context, int textId) {
        new AlertDialog.Builder(context).setTitle(textId)
                .setNeutralButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }*/

    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
