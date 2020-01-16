package com.example.flashlight;

import android.app.Application;

import com.example.flashlight.config.AppPreferences;

public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    public static BaseApplication get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppPreferences.INSTANCE.load(this);
    }
}
