package com.me.ui.app.common.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author tangqi on 17-3-14.
 */
public class RetrofitClient {
    private static final RetrofitClient sInstance = new RetrofitClient();
    public static RetrofitClient getInstance() {
        return sInstance;
    }

    private RetrofitClient() {

    }

    public <T> T buildApi(String baseUrl, Class<T> apiClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpFactory.getsInstance().getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(apiClass);
    }
}
