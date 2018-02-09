package com.allever.coderhouse.utils;

import android.os.Environment;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by allever on 17-6-3.
 */

public class CommonUtil {
    public static String getFileNameFromUrl(String url){
        return (url.substring(url.lastIndexOf("/"))).split("/")[1];
    }

    public static void downloadImage(String url, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

}
