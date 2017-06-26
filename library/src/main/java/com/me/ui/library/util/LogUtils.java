package com.me.ui.library.util;

import android.util.Log;

/**
 * @author tangqi on 17-6-26.
 */
public class LogUtils {

    // log tag by class name
    public static void d(Class clazz, String msg) {
        Log.d(clazz.getSimpleName(), msg);
    }

    public static void i(Class clazz, String msg) {
        Log.i(clazz.getSimpleName(), msg);
    }

    public static void w(Class clazz, String msg) {
        Log.w(clazz.getSimpleName(), msg);
    }

    public static void e(Class clazz, String msg) {
        Log.e(clazz.getSimpleName(), msg);
    }

    // log tag by string
    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}