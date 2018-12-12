package com.me.ui.library.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 沉浸式工具类
 *
 * @author tangqi
 * @since 2018/11/02 13:00
 */
public class StatusBarTranslucentUtils {

    /**
     * 设置状态栏透明
     **/
    public static void setStatusBarTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarColor(Activity activity, int colorResId) {
        // 设置状态栏颜色
        ViewGroup contentLayout = activity.findViewById(android.R.id.content);

        // 添加占位控件
        setupStatusBarView(activity, contentLayout, activity.getResources().getColor(colorResId));

        // 设置Activity的fitsSystemWindows属性
        View contentChild = contentLayout.getChildAt(0);
        contentChild.setFitsSystemWindows(true);
    }

    /**
     * 增加占位控件
     **/
    private static void setupStatusBarView(Activity activity, ViewGroup contentLayout, int color) {
        // 在contentLayout中添加一个和状态栏一样高度的占位控件，并设置和标题栏一样的颜色
        View statusBarView = new View(activity);
        int statusBarHeight = getStatusBarHeight(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        contentLayout.addView(statusBarView, lp);
        statusBarView.setBackgroundColor(color);
    }

    /**
     * 获得状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    private static boolean isXiaoMi() {
        return Build.MANUFACTURER.toUpperCase().contains("XIAOMI");
    }

    private static boolean isMeizu() {
        return Build.MANUFACTURER.toUpperCase().contains("MEIZU");
    }

    /**
     * 设置状态栏文字图标的主题颜色（暗或亮）
     */
    public static void setStatusBarDarkMode(Activity activity, boolean dark) {
        if (isXiaoMi()) {
            // 小米的MUI兼容存在问题，需要同时设置二种方法
            setMiuiStatusBarDarkMode(activity, dark);
        } else if (Build.VERSION.SDK_INT >= 23) {
            // 默认将6.0+系统放在前面
            setAndroidAbove6DarkMode(activity, dark);
        } else if (isMeizu()) {
            // 魅族
            setMeizuStatusBarDarkMode(activity, dark);
        } else {
            // 其他系统
        }
    }

    /**
     * 适配小米系统状态栏黑色字符
     * 注意：为了保证在新旧版本的 MIUI 都能实现「状态栏黑色字符」的效果，需要开发者同时写上以下两种实现方法。
     * 文档：https://dev.mi.com/console/doc/detail?pId=1159
     */
    @SuppressLint("PrivateApi")
    private static boolean setMiuiStatusBarDarkMode(Activity activity, boolean dark) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            // 旧版本MIUI方法
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), dark ? darkModeFlag : 0, darkModeFlag);

            // Android6.0以上系统方法
            if (Build.VERSION.SDK_INT >= 23) {
                setAndroidAbove6DarkMode(activity, dark);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 适配魅族6.0以下状态栏黑色字符
     * 文档：http://open-wiki.flyme.cn/index.php?title=%E7%8A%B6%E6%80%81%E6%A0%8F%E5%8F%98%E8%89%B2}
     */
    @SuppressWarnings("JavaReflectionMemberAccess")
    private static boolean setMeizuStatusBarDarkMode(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * Android6.0以上状态栏黑色字符
     */
    private static void setAndroidAbove6DarkMode(Activity activity, boolean dark) {
        if (activity != null) {
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (dark) {
                    // 用具体值，没用View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR的原因是需要改编译版本为23，
                    // 但是目前尚未兼容6.0系统的权限，所以这里用具体值
                    vis |= 0x00002000;
                } else {
                    vis &= ~(0x00002000);
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }
}
