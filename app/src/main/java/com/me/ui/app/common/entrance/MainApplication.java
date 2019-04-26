package com.me.ui.app.common.entrance;

import android.app.Application;

import com.me.ui.util.Utils;

/**
 * @author tangqi
 * @since 2019/04/27 00:06
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
