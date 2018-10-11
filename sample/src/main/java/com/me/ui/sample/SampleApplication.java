package com.me.ui.sample;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.me.ui.sample.library.log.MLog;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    17/9/21 下午9:23
 */
public class SampleApplication extends Application {

    private static SampleApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Utils.init(this);

        // ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);

        // Logger
        MLog.config();
    }

    public static Context getContext() {
        return mApplication;
    }
}