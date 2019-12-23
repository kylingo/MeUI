package com.me.ui.app.common.entrance;

import android.app.Application;
import android.content.Context;

import com.me.ui.util.Utils;

/**
 * @author tangqi
 * @since 2019/04/27 00:06
 */
public class MainApplication extends Application {

    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Utils.init(this);
    }

    public static Context getContext() {
        return mApplication;
    }
}
