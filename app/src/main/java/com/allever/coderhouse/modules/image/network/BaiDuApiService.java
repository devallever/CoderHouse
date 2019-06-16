package com.allever.coderhouse.modules.image.network;

import com.allever.coderhouse.modules.image.bean.BaiDuImageRoot;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by allever on 17-5-30.
 */

public interface BaiDuApiService {
    //imgs?col=美女&tag=小清新&sort=0&pn=1&rn=10&p=channel&from=1
    @GET("imgs")
    Observable<BaiDuImageRoot> getImageList(
            @Query("col") String col,
            @Query("tag") String tag,
            @Query("sort") String sort,
            @Query("pn") String pn,
            @Query("rn") String rn,
            @Query("p") String p,
            @Query("from") String from
    );
}
