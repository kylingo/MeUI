package com.me.ui.app.wanandroid.network;

import com.me.ui.app.wanandroid.config.WanConstants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author tangqi on 17-3-14.
 */
public class RetrofitClient {
    private static final RetrofitClient sInstance = new RetrofitClient();
    private IWanApi mNetAPi;

    public static RetrofitClient getInstance() {
        return sInstance;
    }

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WanConstants.BASE_URL)
                .client(OkHttpFactory.getsInstance().getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mNetAPi = retrofit.create(IWanApi.class);
    }

    public IWanApi getNetApi() {
        return mNetAPi;
    }
}
