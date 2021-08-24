package com.krad.origin.hoframe.pager;

import android.app.Application;

public class HoApplication extends Application {

    private static HoApplication instance;

    public static HoApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
