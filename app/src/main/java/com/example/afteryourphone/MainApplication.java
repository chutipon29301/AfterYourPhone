package com.example.afteryourphone;

import android.app.Application;

import com.example.afteryourphone.util.Contextor;

/**
 * Created by admin on 17/2/2018 AD.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
    }
}
