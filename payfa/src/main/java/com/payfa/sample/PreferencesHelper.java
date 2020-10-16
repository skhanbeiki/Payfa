package com.payfa.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PreferencesHelper {
    public static String PREF_FILE_NAME;

    @SuppressLint("ObsoleteSdkInt")
    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        PREF_FILE_NAME = context.getPackageName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    @SuppressLint("ApplySharedPref")
    public static void clearToPreferences(Context context, String preferenceName) {
        PREF_FILE_NAME = context.getPackageName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(preferenceName).commit();
    }

    @SuppressLint("ApplySharedPref")
    public static void clearAllPreferences(Context context) {
        PREF_FILE_NAME = context.getPackageName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        PREF_FILE_NAME = context.getPackageName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void saveToPreferences(Context context, String preferenceName, boolean preferenceValue) {
        PREF_FILE_NAME = context.getPackageName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(preferenceName, preferenceValue);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }

    }

    public static boolean readFromPreferences(Context context, String preferenceName, boolean defaultValue) {
        PREF_FILE_NAME = context.getPackageName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(preferenceName, defaultValue);
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void saveToPreferences(Context context, String preferenceName, int preferenceValue) {
        PREF_FILE_NAME = context.getPackageName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(preferenceName, preferenceValue);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }


    public static int readFromPreferences(Context context, String preferenceName, int defaultValue) {
        PREF_FILE_NAME = context.getPackageName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(preferenceName, defaultValue);
    }
}