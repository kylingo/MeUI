package com.me.ui.sample.library.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * @author kylingo
 * @since 2018/12/17 13:49
 */
public class UriCompat {

    private final static String AUTHORITY = "com.me.ui.sample.fileProvider";

    public static Uri getUriForFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, AUTHORITY, file);
        } else {
            return Uri.fromFile(file);
        }
    }
}
