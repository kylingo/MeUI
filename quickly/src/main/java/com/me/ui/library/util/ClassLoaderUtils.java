package com.me.ui.library.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.io.File;
import java.util.List;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * * 插件加载管理
 * * DexClassLoader ：可以加载文件系统上的jar、dex、apk
 * * PathClassLoader ：可以加载/data/app目录下的apk，这也意味着，它只能加载已经安装的apk
 * * URLClassLoader ：可以加载java中的jar，但是由于dalvik不能直接识别jar，所以此方法在android中无法使用，尽管还有这个类
 *
 * @author kylingo
 * @since 2018/12/07 15:04
 */
public class ClassLoaderUtils {
    private static final String TAG = "PluginLoader";

    public static Class<?> loadDexClass(Context context, String dexFilePath, String className) {
        if (context == null) {
            return null;
        }

        // 4.1以后不能把dex文件放sd卡，会出现java.lang.IllegalArgumentException
        File dexOutputDir = context.getDir("dex", Context.MODE_PRIVATE);
        Log.i(TAG, "loadDexClass dexFilePath: " + dexFilePath);
        Log.i(TAG, "loadDexClass dexOutputDir: " + dexOutputDir);

        // 定义DexClassLoader
        // 第一个参数：是dex压缩文件的路径
        // 第二个参数：是dex解压缩后存放的目录
        // 第三个参数：是C/C++依赖的本地库文件目录,可以为null
        // 第四个参数：是上一级的类加载器
        DexClassLoader cl = new DexClassLoader(dexFilePath, dexOutputDir.getAbsolutePath(),
                null, context.getClassLoader());
        Log.i(TAG, "loadDexClass class loader: " + cl);
        Class<?> libProviderClazz = null;
        try {
            libProviderClazz = cl.loadClass(className);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return libProviderClazz;
    }

    /**
     * 只能加载已安装的apk
     */
    public static Class<?> loadPathClass(Context context, String pluginAction, String className) {
        if (context == null) {
            return null;
        }

        Intent intent = new Intent(pluginAction, null);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(intent, 0);
        if (resolveInfoList == null || resolveInfoList.size() < 1) {
            Log.e(TAG, "resolveInfoList error");
            return null;
        }

        ActivityInfo actInfo = resolveInfoList.get(0).activityInfo;
        String apkPath = actInfo.applicationInfo.sourceDir;
        String libPath = actInfo.applicationInfo.nativeLibraryDir;
        Log.i(TAG, "loadPathClass apkPath =" + apkPath + ", libPath =" + libPath);

        // 创建类加载器，把dex加载到虚拟机中
        // 第一个参数：是指定apk安装的路径，这个路径要注意只能是通过actInfo.applicationInfo.sourceDir来获取
        // 第二个参数：是C/C++依赖的本地库文件目录,可以为null
        // 第三个参数：是上一级的类加载器
        PathClassLoader pcl = new PathClassLoader(apkPath, libPath, context.getClassLoader());
        Class<?> libProviderClazz = null;
        try {
            libProviderClazz = pcl.loadClass(className);
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e(TAG, "lib init error e=" + exception.toString());
        }

        return libProviderClazz;
    }
}
