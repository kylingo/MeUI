package com.me.ui.app.wanandroid.network;

import rx.Observable;

/**
 * @author tangqi on 17-3-14.
 */
public class NetEngine {

    private final IWanApi mWanApi;
    private static final NetEngine sInstance = new NetEngine();

    public static NetEngine getInstance() {
        return sInstance;
    }

    private NetEngine() {
        mWanApi = RetrofitClient.getInstance().getNetApi();
    }

    public Observable<Object> getMainArticleList(int page) {
        return mWanApi.getMainArticleList(page);
    }
}
