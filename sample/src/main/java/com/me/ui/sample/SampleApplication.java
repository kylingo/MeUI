package com.me.ui.sample;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    17/9/21 下午9:23
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);

        // ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
}