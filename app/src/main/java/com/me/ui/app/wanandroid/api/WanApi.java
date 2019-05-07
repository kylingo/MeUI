package com.me.ui.app.wanandroid.api;

import com.me.ui.app.wanandroid.data.WanArticleBean;
import com.me.ui.app.wanandroid.data.WanBannerBean;
import com.me.ui.app.wanandroid.data.WanHotKeyBean;
import com.me.ui.app.wanandroid.data.WanListModule;
import com.me.ui.app.wanandroid.data.WanModule;
import com.me.ui.app.wanandroid.data.WanTreeBean;
import com.me.ui.app.wanandroid.data.WanWebsiteBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author tangqi
 * @since 2019/04/22 21:00
 */
public interface WanApi {

    @GET("article/list/{page}/json")
    Observable<WanModule<WanArticleBean>> getMainArticleList(@Path("page") int page);

    @GET("banner/json")
    Observable<WanListModule<WanBannerBean>> getMainBanner();

    @GET("friend/json")
    Observable<WanListModule<WanWebsiteBean>> getMainWebsite();

    @GET("hotkey/json")
    Observable<WanListModule<WanHotKeyBean>> getMainHotKey();

    @GET("tree/json")
    Observable<WanListModule<WanTreeBean>> getTree();

    @GET("article/list/{page}/json")
    Observable<WanModule<WanArticleBean>> getTreeCategory(@Path("page") int page, @Query("cid") int cid);
}
