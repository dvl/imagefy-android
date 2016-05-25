package com.vanhackathon.imagefy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rodrigo on 5/21/2016.
 */
public class LocalLogin {

    public static final String LOGGED = "logged";

    public static void logeIn(Context context, String loginToken) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LOGGED, loginToken);
        editor.commit();
    }

    public static void logeOut(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(LOGGED);
        editor.commit();
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_preference), Context.MODE_PRIVATE);
        return sharedPref.getString("logged", null) != null;
    }

    public static String loginToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_preference), Context.MODE_PRIVATE);
        return sharedPref.getString("logged", null);
    }
}
