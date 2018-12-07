package com.me.ui.sample.library.plugin;

import android.content.Context;

import com.me.ui.library.util.ClassLoaderUtils;

import java.io.File;

/**
 * @author kylingo
 * @since 2018/12/07 15:42
 */
public class PluginLoader {
    private static final String TAG = "PluginLoader";

    // 需要将assets目录的以下资源放到SD卡"/storage/emulated/0/Android/data/com.kylingo.plugin/cache/"
    public static final String PLUGIN_DEX = "plugin_dex.jar";
    public static final String PLUGIN_CLASS = "custom-debug.apk";

    // 插件类名称
    public static final String PLUGIN_IMPL = "com.kylingo.plugin.custom.PluginImpl";

    // 插件Intent名称
    public static final String PLUGIN_ACTION = "com.plugin.impl";

    public static Class<?> loadDexClass(Context context, String fileName, String className) {
        String dexFilePath = context.getExternalCacheDir() + File.separator + fileName;
        return ClassLoaderUtils.loadDexClass(context, dexFilePath, className);
    }

    /**
     * 只能加载已安装的apk
     */
    public static Class<?> loadPathClass(Context context, String pluginAction, String className) {
        return ClassLoaderUtils.loadPathClass(context, pluginAction, className);
    }
}
