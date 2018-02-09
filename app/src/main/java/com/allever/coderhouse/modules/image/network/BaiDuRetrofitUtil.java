package com.allever.coderhouse.modules.image.network;

import com.allever.coderhouse.modules.image.bean.BaiDuImageRoot;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allever on 17-5-30.
 */

public class BaiDuRetrofitUtil {

    private static final String TAG = "BaiDuRetrofitUtil";
    private Retrofit retrofit;
    private BaiDuApiService baiDuApiService;
    private static final String BASE_URL = "http://image.baidu.com/data/";

    //imgs?col=美女&tag=小清新&sort=0&pn=1&rn=10&p=channel&from=1
    private static final String SORT = "0";
    private static final String RN = "10"; //请求数量
    private static final String P = "channel";
    private static final String FORM = "1";


    private BaiDuRetrofitUtil(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        baiDuApiService = retrofit.create(BaiDuApiService.class);
    }

    public static BaiDuRetrofitUtil getInstance(){
        return BaiDuRetrofitHolder.INSTANCE;
    }

    public void getImageList(Subscriber<BaiDuImageRoot> subscriber, String col,String tag,String pn){
        baiDuApiService.getImageList(col, tag, SORT,pn,RN,P,FORM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }




    private static class BaiDuRetrofitHolder{
        private static final BaiDuRetrofitUtil INSTANCE = new BaiDuRetrofitUtil();
    }
}