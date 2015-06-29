package cat.lafosca.smartcitizen.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by ferran on 16/06/15.
 */
public class SharedPreferencesManager {

    private final String SHARED_PREFS = "smart-citizen";

    private final String SP_TOKEN_KEY = "token";

    private static SharedPreferencesManager ourInstance = new SharedPreferencesManager();

    public static SharedPreferencesManager getInstance() {
        return ourInstance;
    }

    private SharedPreferencesManager() {
    }

    private Context mContext;

    public void init(Context ctx) {
        if (this.mContext == null) {
            this.mContext = ctx;
        }
    }

    public boolean isUserLoggedIn() {
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String token = preferences.getString(SP_TOKEN_KEY, "");

        return !TextUtils.isEmpty(token);
    }

    public void setUserLoggedIn(String accesToken) {
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        preferences.edit().putString(SP_TOKEN_KEY, accesToken).commit();
    }

    //make it part of the singleton instead of read it from shared preferences?
    public String getUserToken() {
        return mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).getString(SP_TOKEN_KEY, "");
    }

}
