package com.sunny.softapp.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class App extends Application {
    private static final String KEY_VIBRATOR = "KEY_VIBRATOR";
    //    private static final String KEY_GESTURE = "KEY_GESTURE";
    private static final String KEY_FREQUENCY = "KEY_FREQUENCY";
    public static Context appContext;
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences("APP", Context.MODE_PRIVATE);
        }
    }

    public static void setVibratorStatus(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_VIBRATOR, b);
        editor.apply();
    }

    public static boolean getVibratorStatus() {
        return sharedPreferences.getBoolean(KEY_VIBRATOR, true);
    }

    public static Long getFrequencyStatus() {
        return sharedPreferences.getLong(KEY_FREQUENCY, 200);
    }

    public static void setFrequencyStatus(Long i) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_FREQUENCY, i);
        editor.apply();
    }

}
