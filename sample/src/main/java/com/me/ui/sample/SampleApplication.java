package com.me.ui.sample;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.me.ui.sample.library.log.MLog;
import com.me.ui.util.ProcessUtils;
import com.me.ui.util.Utils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;

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

        // Bugly
        UserStrategy strategy = new UserStrategy(getApplicationContext());
        strategy.setUploadProcess(ProcessUtils.isMainProcess());
        Bugly.init(getApplicationContext(), "dd620d6b4a", false, strategy);
    }

    public static Context getContext() {
        return mApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}