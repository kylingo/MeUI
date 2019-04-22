package com.me.ui.app.wanandroid.network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author tangqi
 * @since 2019/04/22 21:00
 */
public interface IWanApi {

    @GET("article/list/{page}/json")
    Observable<Object> getMainArticleList(@Path("page") int page);
}
