package com.me.ui.sample.library.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author kylingo
 * @since 2018/12/07 15:54
 */
public class CopyUtils {

    public static String getCachePath(Context context) {
        if (context.getExternalCacheDir() != null) {
            return context.getExternalCacheDir().getAbsolutePath();
        } else {
            return context.getCacheDir().getAbsolutePath();
        }
    }

    public static void assetsCopy(Context context, String assetsPath, String dirPath) {
        File file = new File(dirPath);

        AssetManager manager = context.getAssets();
        try {
            String[] list = manager.list(assetsPath);
            if (list.length == 0) { // 文件
                InputStream in = manager.open(assetsPath);
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileOutputStream fout = new FileOutputStream(file);
                // 复制
                byte[] buf = new byte[1024];
                int count;
                while ((count = in.read(buf)) != -1) {
                    fout.write(buf, 0, count);
                    fout.flush();
                }
                in.close();
                fout.close();
            } else { // 目录
                for (String path : list) {
                    assetsCopy(context, assetsPath + "/" + path, dirPath + "/" + path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
