package eu.bubu1.logintest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static eu.bubu1.logintest.utils.PreferencesUtility.*;

public class SaveSharedPreference {
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     *
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn, String username, String serverUri, String clientToken) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.putString(USERNAME_PREF, username);
        editor.putString(SERVER_URI_PREF, serverUri);
        editor.putString(CLIENT_TOKEN_SECRET_PREF, clientToken);
        editor.apply();
    }


    /**
     * Get the Login Status
     *
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedInStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }
    public static String getLoggedInUsername(Context context) {
        return getPreferences(context).getString(USERNAME_PREF, "");
    }
    public static String getLoggedInServerUri(Context context) {
        return getPreferences(context).getString(SERVER_URI_PREF, "");
    }
    public static String getClientToken(Context context) {
        return getPreferences(context).getString(CLIENT_TOKEN_SECRET_PREF, "");
    }
}
