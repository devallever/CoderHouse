package com.allever.coderhouse.modules.main.network;

import com.allever.coderhouse.modules.main.bean.GuessRoot;
import com.allever.coderhouse.modules.main.bean.Root;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by allever on 17-5-27.
 */

public interface GanHuoApiService {
    @GET("data/{type}/{size}/{page}")
    Observable<Root> getDataList(
            @Path("type") String type,
            @Path("size") int size,
            @Path("page") int page);

    @GET("search/query/listview/category/{type}/count/{size}/page/{pageCount}")
    Observable<GuessRoot> getGuessLikeList(
            @Path("type") String type,
            @Path("size") int size,
            @Path("pageCount") int pageCount
    );


}
