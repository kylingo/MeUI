package com.me.ui.sample.library.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author kylingo
 * @since 2018/11/26 12:31
 */
public class FragmentUtils {

    /**
     * Show fragment with commitAllowingStateLoss
     */
    public static void show(FragmentActivity activity, Fragment fragment, String tag) {
        if (isDestroyed(activity)) {
            return;
        }

        FragmentManager manager = activity.getSupportFragmentManager();
        if (manager == null) {
            return;
        }

        // Remove last fragment instance
        FragmentTransaction ft = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }

        // Add fragment with commitAllowingStateLoss
        ft.add(fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public static void show(Context context, Fragment fragment, String tag) {
        if (context instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) context;
            show(activity, fragment, tag);
        }
    }

    private static boolean isDestroyed(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return activity.isDestroyed();
            } else {
                return activity.isFinishing();
            }
        }
        return true;
    }
}
