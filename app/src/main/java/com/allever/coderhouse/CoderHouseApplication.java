package com.allever.coderhouse;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by allever on 17-5-27.
 */

public class CoderHouseApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        LitePal.initialize(this);
    }

    public static Context getContext(){
        return context;
    }
}
