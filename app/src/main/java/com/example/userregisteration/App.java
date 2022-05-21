package com.example.userregisteration;

import android.app.Application;

import android.content.Context;

public class App extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    private static App instance;

    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}
