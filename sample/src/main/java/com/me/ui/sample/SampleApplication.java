package com.me.ui.sample;

import android.app.Application;

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
    }
}