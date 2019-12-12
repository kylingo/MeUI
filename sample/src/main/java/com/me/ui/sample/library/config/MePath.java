package com.me.ui.sample.library.config;

import com.me.ui.sample.Global;

import java.io.File;

/**
 * @author kylingo on 18/10/11
 */
public class MePath {

    public static final String PATH_LOG = getCachePath("log");

    private static String getCachePath(String path) {
        File cacheDir = Global.getContext().getExternalCacheDir();
        if (cacheDir != null) {
            File child = new File(cacheDir, path);
            if (!child.exists()) {
                child.mkdirs();
            }
            return child.getAbsolutePath();
        }

        return null;
    }
}
