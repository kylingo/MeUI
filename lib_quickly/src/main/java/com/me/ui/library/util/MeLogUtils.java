package com.me.ui.library.util;

import android.util.Log;

/**
 * @author tangqi on 17-6-26.
 */
public class MeLogUtils {

    private static boolean flag = true;

    // log control
    public static void setState(boolean enable) {
        flag = enable;
    }

    // log tag by class name
    public static void d(Class clazz, String msg) {
        d(clazz.getSimpleName(), msg);
    }

    public static void i(Class clazz, String msg) {
        i(clazz.getSimpleName(), msg);
    }

    public static void w(Class clazz, String msg) {
        w(clazz.getSimpleName(), msg);
    }

    public static void e(Class clazz, String msg) {
        e(clazz.getSimpleName(), msg);
    }

    // log tag by string
    public static void d(String tag, String msg) {
        if (flag) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (flag) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}