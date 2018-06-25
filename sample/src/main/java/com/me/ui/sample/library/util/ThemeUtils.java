package com.me.ui.sample.library.util;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.SPUtils;
import com.me.ui.sample.R;
import com.me.ui.sample.library.config.SpKey;

/**
 * @author kylingo on 18/6/22
 */
public class ThemeUtils {

    /**
     * Change theme
     *
     * @see #reCreate(Activity) take effect
     */
    public static void changeTheme(boolean isLight) {
        SPUtils.getInstance().put(SpKey.THEME, isLight);
    }

    public static void setTheme(@NonNull Activity activity) {
        boolean isLight = SPUtils.getInstance().getBoolean(SpKey.THEME, true);
        activity.setTheme(isLight ? R.style.AppLightTheme : R.style.AppDarkTheme);
    }

    public static void reCreate(@NonNull final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.recreate();
            }
        });
    }
}
