package com.allever.coderhouse.utils;

import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.allever.coderhouse.CoderHouseApplication;

/**
 * Created by allever on 17-5-27.
 */

public class SharePreferenceUtil {

    private SharePreferenceUtil(){
        //SharedPreferences sharedPreferences = CoderHouseApplication.getContext().getSharedPreferences()
    }

    public static void addTypeCount(String type){
        SharedPreferences sharedPreferences = CoderHouseApplication.getContext().getSharedPreferences("coder_pref", CoderHouseApplication.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int typeCount = sharedPreferences.getInt("type_count",0);
        editor.putInt("type_count", (typeCount++));
        editor.commit();
    }

    public static SharePreferenceUtil getInstance(){
        return SharePreferenceHolder.INSTANCE;
    }

    private static class SharePreferenceHolder{
        public static final SharePreferenceUtil INSTANCE = new SharePreferenceUtil();
    }
}
