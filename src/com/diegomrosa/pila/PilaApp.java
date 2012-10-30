package com.diegomrosa.pila;

import android.app.Application;
import android.content.Context;

public class PilaApp  extends Application {
    private static PilaApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
