package com.me.ui.sample.library.util;

import android.content.Context;
import android.content.Intent;
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

    public static void addUriPermission(Intent intent) {
        // java.lang.SecurityException: Permission Denial: opening provider android.support.v4.content.FileProvider
        // from ProcessRecord{14ab7d 20766:com.miui.packageinstaller/u0a22} (pid=20766, uid=10022) that is not exported from uid 10209
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

}
