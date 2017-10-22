package com.allever.coderhouse.modules.main.network;

import com.allever.coderhouse.modules.main.bean.GuessRoot;
import com.allever.coderhouse.modules.main.bean.Root;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allever on 17-5-27.
 */

public class GanHuoRetrofitUtil {

    private static final String TAG = "GanHuoRetrofitUtil";
    //private static final String BASE_URL = "http://27.54.249.252:8080/SocialServer/";
    private static final String BASE_URL = "http://www.gank.io/api/";
    private Retrofit retrofit;
    private GanHuoApiService ganHuoApiService;

    private GanHuoRetrofitUtil(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        ganHuoApiService = retrofit.create(GanHuoApiService.class);
    }

    public static GanHuoRetrofitUtil getInstance(){
        return GanHuoRetrofitHolder.INSTANCE;
    }

    public void getDataList(Subscriber<Root> subscriber, String type,int size, int page){
        ganHuoApiService.getDataList(type,size,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    public void getGessLikeList(Subscriber<GuessRoot> subscriber, String type, int size, int page){
        ganHuoApiService.getGuessLikeList(type,size,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }



    private static class GanHuoRetrofitHolder{
        private static final GanHuoRetrofitUtil INSTANCE = new GanHuoRetrofitUtil();
    }
}
