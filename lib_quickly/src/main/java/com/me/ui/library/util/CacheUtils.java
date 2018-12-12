package com.me.ui.library.util;

import android.content.Context;

import java.io.File;

/**
 * @author kylingo on 18/6/22
 */
public class CacheUtils {

    /**
     * 获取缓存根目录
     */
    public static String getCachePath(Context context) {
        String rootPath;
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir != null) {
            rootPath = externalCacheDir.getAbsolutePath();
        } else {
            rootPath = context.getCacheDir().getAbsolutePath();
        }

        return rootPath;
    }

    /**
     * 截屏图片路径
     */
    public static String getScreenShotPath(Context context, String type) {
        String filePath = getCachePath(context).concat(File.separator).concat("ScreenShot")
                .concat(File.separator).concat(type);
        File file = new File(filePath);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }

        return filePath.concat(File.separator).concat(String.valueOf(System.currentTimeMillis()))
                .concat(".png");
    }
}
