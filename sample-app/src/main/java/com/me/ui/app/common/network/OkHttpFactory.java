package com.me.ui.app.common.network;


import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.me.ui.app.common.entrance.MainApplication;
import com.me.ui.library.helper.MeLogger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author tangqi on 17-3-14.
 */
public class OkHttpFactory {

    private static final String TAG = "OkHttpFactory";
    private static final int TIMEOUT_CONNECT = 15;
    private OkHttpClient.Builder mBuilder;
    private static final OkHttpFactory sInstance = new OkHttpFactory();

    public static OkHttpFactory getsInstance() {
        return sInstance;
    }

    private OkHttpFactory() {
        mBuilder = new OkHttpClient.Builder();
        mBuilder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        MeLogger.i(TAG, message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY));
        mBuilder.cookieJar(new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(MainApplication.getContext())));
    }

    public OkHttpClient getOkHttpClient() {
        return mBuilder.build();
    }

}
