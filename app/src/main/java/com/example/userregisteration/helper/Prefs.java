package com.example.userregisteration.helper;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.userregisteration.App;


public class Prefs {

    static SharedPreferences sharedPref = null;

    private static SharedPreferences getSharedPref() {
        if (sharedPref == null) {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(App.getInstance().getApplicationContext());
        }
        return sharedPref;
    }

    private static SharedPreferences.Editor getEditor() {
        return getSharedPref().edit();
    }

    public static String getString(String key) {
        return getSharedPref().getString(key, null);
    }

    public static boolean getBoolean(String key) {
        return getSharedPref().getBoolean(key, false);
    }

    public static int getInt(String key) {
        return getSharedPref().getInt(key, 0);
    }

    public static long getLong(String key) {
        return getSharedPref().getLong(key, 0);
    }

    public static void setString(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    public static void setBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    public static void setInt(String key, int value) {
        getEditor().putInt(key, value).apply();
    }

    public static void setLong(String key, long value) {
        getEditor().putLong(key, value).apply();
    }

}