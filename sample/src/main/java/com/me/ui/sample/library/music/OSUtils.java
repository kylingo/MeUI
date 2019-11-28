package com.me.ui.sample.library.music;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

/**
 * @author laiwenjie 1/15/15
 */
public class OSUtils {

    private static String platform;

    public static boolean isScreenOn() {
        Context context = Global.appContext;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            boolean screenOn = false;
            try {
                for (Display display : dm.getDisplays()) {
                    if (display.getState() != Display.STATE_OFF) {
                        screenOn = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return screenOn;
        } else {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            return pm.isScreenOn();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isScreenLocked() {
        KeyguardManager keyguardManager = (KeyguardManager) Global.appContext
                .getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.isKeyguardLocked();
    }

    public static boolean isLockWidgetVisible() {
        return isScreenLocked() && isScreenOn();
    }

    // 获取屏幕的尺寸
    public static int[] getScreenSize(Context context) {
        int[] screenSize = new int[2];
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        screenSize[0] = display.getWidth();
        screenSize[1] = display.getHeight();
        return screenSize;
    }

    // 获取屏幕的尺寸
    public static int[] getScreenSize2(Context context) {
        int[] screenSize = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        screenSize[0] = screenWidth;
        screenSize[1] = screenHeight;
        return screenSize;
    }

    /**
     * User-Agent
     * 
     * @return user-agent
     */
    public static String getUser_Agent(Context ctx) {
        String ua = "Android;" + getOSVersion() + ";" + getVersionName(ctx) + ";" + getVersionCode(ctx) + ";"
                + getChannelName(ctx) + ";" + getVendor() + "-" + getDevice();

        return ua;
    }

    /**
     * device model name, e.g: GT-I9100 得到设备型号
     * 
     * @return the user_Agent
     */
    public static String getDevice() {
        return Build.MODEL;
    }

    /**
     * device factory name, e.g: Samsung 得到品牌
     * 
     * @return the vENDOR
     */
    public static String getVendor() {
        return Build.BRAND;
    }

    /**
     * @return the SDK version
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * @return the OS version
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Retrieves application's version number from the manifest 得到版本名
     * 
     * @return versionName
     */
    public static String getVersionName(Context ctx) {
        String versionName = "0.0.0";
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    /**
     * @MethodName：getVersionCode
     * @tags：@return
     * @return_type：int
     * @exception
     * @TODO: 得到版本号
     * @since
     */
    public static int getVersionCode(Context ctx) {
        int versionCode = 0;
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    public static void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 获取渠道名
     * 
     * @param ctx
     *            此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Context ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                // 注意此处为ApplicationInfo 而不是
                // ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(),
                        PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString("");
                    }
                }

            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    /**
     * 获取application中指定的meta-data
     * 
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(),
                        PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = Global.appContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getWithDp() {
        final float scale = Global.appContext.getResources().getDisplayMetrics().density;
        return (int) (getScreenSize2(Global.appContext)[0] / scale + 0.5f);
    }

    @SuppressLint("NewApi")
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> apps = mActivityManager.getRunningAppProcesses();
        if (apps != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : apps) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        }
        return null;
    }

    public static String getCurrentLanguageType() {
        Locale mlocal = Global.appContext.getResources().getConfiguration().locale;
        if (mlocal != null) {
            return mlocal.getLanguage();
        }
        return "";
    }

    public static int getUid(Context context) {
        return context.getApplicationInfo().uid;
    }

    public static boolean isMSM8994() {
        return "msm8994".equalsIgnoreCase(getPlatform());
    }

    public static String getPlatform() {
        if (TextUtils.isEmpty(platform)) {
            String line = "";
            Process ifc = null;
            try {
                ifc = Runtime.getRuntime().exec("getprop ro.board.platform");
                BufferedReader bis = new BufferedReader(new InputStreamReader(ifc.getInputStream()));
                line = bis.readLine();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } finally {
                if (ifc != null) {
                    ifc.destroy();
                }
            }
            if (TextUtils.isEmpty(line)) {
                line = "unknown";
            }
            platform = line;
        }

        return platform;
    }
    
    public static boolean isTargetActivityAvailable(Context context,Intent intent){
        if(context!=null){
          PackageManager pm = context.getPackageManager();  
          if(pm!=null){
             ComponentName cn = intent.resolveActivity(pm);  
             if(cn!=null){
               return true; 
             }
          }
       }
       return false;
    }
}