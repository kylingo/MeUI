package com.me.ui.library.util;

import android.content.Context;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    17/9/20 下午10:32
 */
public class MeViewUtils {

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}