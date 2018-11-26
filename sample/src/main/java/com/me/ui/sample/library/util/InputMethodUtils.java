package com.me.ui.sample.library.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author kylingo
 * @since 2018/11/26 12:53
 */
public class InputMethodUtils {

    public static void showKeyboard(Context context, View targetView) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(targetView, 0);
        }
    }

    public static void hideKeyboard(Context context, View targetView) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(targetView.getWindowToken(), 0);
        }
    }
}
