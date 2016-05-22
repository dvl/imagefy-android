package com.vanhackathon.imagefy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rodrigo on 5/21/2016.
 */
public class LocalLoginManager {

    public static final String LOGGED = "logged";

    public static void logeIn(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(LOGGED, true);
        editor.commit();
    }

    public static void logeOut(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(LOGGED);
        editor.commit();
    }

    public static boolean loggedIn(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_preference), Context.MODE_PRIVATE);
        return sharedPref.getBoolean("logged", false);
    }
}
