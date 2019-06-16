package com.allever.coderhouse.modules.image.network;

import com.allever.coderhouse.modules.image.bean.BingRoot;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allever on 17-5-29.
 */

public class BingRetrofitUtil {

    private static final String TAG = "BingRetrofitUtil";
    private Retrofit retrofit;
    private BingApiService bingApiService;
    private static final String BASE_URL = "http://39.108.9.138:8080/BingServer/";

    private BingRetrofitUtil(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        bingApiService = retrofit.create(BingApiService.class);
    }

    public static BingRetrofitUtil getInstance(){
        return BingRetrofitHolder.INSTANCE;
    }

    public void getBingImageList(Subscriber<BingRoot> subscriber, String page){
        bingApiService.getBingImageList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }


    private static class BingRetrofitHolder{
        private static final BingRetrofitUtil INSTANCE = new BingRetrofitUtil();
    }
}