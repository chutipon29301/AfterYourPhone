package com.example.afteryourphone.util;

import android.content.Context;

/**
 * Created by admin on 7/12/2017 AD.
 */

public class Contextor {
    private static Contextor instance;
    private Context context;

    private Contextor() {
    }

    public static Contextor getInstance() {
        if (instance == null) {
            instance = new Contextor();
        }
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void init(Context context) {
        this.context = context;
    }
}
