package com.allever.coderhouse.modules.image.network;

import com.allever.coderhouse.modules.image.bean.BingRoot;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by allever on 17-5-29.
 */

public interface BingApiService {
    @GET("BingImageListServlet")
    Observable<BingRoot> getBingImageList(@Query("page") String page);
}
