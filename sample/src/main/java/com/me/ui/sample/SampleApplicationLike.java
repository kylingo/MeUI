package com.me.ui.sample;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.me.ui.sample.library.log.MLog;
import com.me.ui.util.ProcessUtils;
import com.me.ui.util.Utils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    17/9/21 下午9:23
 */
public class SampleApplicationLike extends DefaultApplicationLike {

    private static Application mApplication;

    public SampleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                                 long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Application application = getApplication();
        mApplication = application;
        Utils.init(application);

        // ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(application);

        // Logger
        MLog.config();

        // Bugly
        UserStrategy strategy = new UserStrategy(application.getApplicationContext());
        strategy.setUploadProcess(ProcessUtils.isMainProcess());
        Bugly.init(application.getApplicationContext(), "dd620d6b4a", false, strategy);
    }

    public static Context getContext() {
        return mApplication;
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
    }
}