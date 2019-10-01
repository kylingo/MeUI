package com.me.ui.sample.pattern.structure.proxy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class SimpleService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d(SimpleService.class, "onBind");
        return new SimpleBinder();
    }
}
