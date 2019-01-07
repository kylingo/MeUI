package com.me.ui.sample.library.config;

import com.me.ui.sample.SampleApplicationLike;

import java.io.File;

/**
 * @author kylingo on 18/10/11
 */
public class MePath {

    public static final String PATH_LOG = getCachePath("log");

    private static String getCachePath(String path) {
        File cacheDir = SampleApplicationLike.getContext().getExternalCacheDir();
        if (cacheDir != null) {
            File child = new File(cacheDir, path);
            if (child.mkdirs()) {
                return child.getAbsolutePath();
            }
        }

        return null;
    }
}
