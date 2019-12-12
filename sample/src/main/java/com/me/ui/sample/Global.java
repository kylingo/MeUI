package com.me.ui.sample;

import android.content.Context;

/**
 * @author kylingo
 * @since 2019/12/12 14:37
 */
public class Global {

    public static Context appContext = getContext();

    public static Context getContext() {
        return SampleApplication.getContext();
    }
}
