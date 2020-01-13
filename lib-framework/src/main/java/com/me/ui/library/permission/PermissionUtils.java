package com.me.ui.library.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.me.ui.library.permission.helper.PermissionCompat;
import com.me.ui.library.permission.helper.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限检查（设置targetSDK 大于 23）
 *
 * @author kylingo on 18/8/30
 */

public class PermissionUtils {
    private static final String TAG = "PermissionUtils";

    public static final int PERMISSION_CODE_LOCATION = 0x01;
    public static final int PERMISSION_CODE_READ_SMS = 0x02;
    public static final int PERMISSION_CODE_CALL_PHONE = 0x03;
    public static final int PERMISSION_CODE_READ_CALL_LOG = 0x04;
    public static final int PERMISSION_CODE_WRITE_CALL_LOG = 0x05;
    public static final int PERMISSION_CODE_READ_CONTACTS = 0x06;
    public static final int PERMISSION_CODE_READ_PHONE_STATE = 0x07;
    public static final int PERMISSION_CODE_WRITE_EXTERNAL_STORAGE = 0x08;
    public static final int PERMISSION_CODE_CAMERA = 0x09;
    public static final int PERMISSION_CODE_RECORD_AUDIO = 0x10;
    public static final int PERMISSION_CODE_SETTING = 0x11;

    /**
     * 获取权限回调接口
     */
    public interface PermissionsCallback extends ActivityCompat.OnRequestPermissionsResultCallback {
        // 权限被允许
        void onPermissionsGranted(int requestCode, List<String> perms);

        // 权限被拒绝，权限设置对话框统一在此展示
        void onPermissionsDenied(int requestCode, List<String> perms);
    }

    /**
     * 判断是否低于6.0，需要权限判断
     */
    private static boolean isLowApi() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

    /**
     * 检查权限
     */
    public static boolean hasPermissions(Context context, String... perms) {
        // Android6.0以下默认返回为true
        if (isLowApi()) {
            return true;
        }

        if (context == null) {
            // Fix bugly #573624 IllegalArgumentException
            return false;
//            throw new IllegalArgumentException("hasPermissions() context is null");
        }

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查权限和申请权限
     *
     * @param activity    权限申请上下文，可以为activity，fragment，context
     * @param rationale   非必需参数，目前产品需求不需要权限解释框
     * @param requestCode 申请码，用于区分回调
     * @param perms       权限组
     * @see PermissionsCallback 权限回调
     */
    public static boolean checkPermissions(Activity activity, String rationale, int requestCode, String... perms) {
        if (isLowApi()) {
            return PermissionCompat.checkFakePermissions(activity, requestCode, perms);
        }

        boolean result = hasPermissions(activity, perms);
        if (!result) {
            requestPermissions(activity, rationale, requestCode, perms);
        } else {
            result = PermissionCompat.checkFakePermissions(activity, requestCode, perms);
        }
        return result;
    }

    public static boolean checkPermissions(Fragment fragment, String rationale, int requestCode, String... perms) {
        if (isLowApi()) {
            return PermissionCompat.checkFakePermissions(fragment, requestCode, perms);
        }

        boolean result = hasPermissions(fragment.getContext(), perms);
        if (!result) {
            requestPermissions(fragment, rationale, requestCode, perms);
        } else {
            result = PermissionCompat.checkFakePermissions(fragment, requestCode, perms);
        }
        return result;
    }

    /**
     * 此方法用于非Activity或Fragment，例如adapter，util中使用，权限回调到Activity中。
     *
     * @param context must be activity
     */
    public static boolean checkPermissions(Context context, String rationale, int requestCode, String... perms) {
        if (context instanceof Activity) {
            return checkPermissions((Activity) context, rationale, requestCode, perms);
        }

        return hasPermissions(context, perms);
    }

    /**
     * 请求权限，不要直接调用
     *
     * @see #checkPermissions(Activity, String, int, String...)
     */
    private static void requestPermissions(Activity activity, String rationale, int requestCode, String... perms) {
        PermissionHelper.newInstance(activity).requestPermissions(rationale, requestCode, perms);
    }

    private static void requestPermissions(Fragment fragment, String rationale, int requestCode, String... perms) {
        PermissionHelper.newInstance(fragment).requestPermissions(rationale, requestCode, perms);
    }

    /**
     * 权限回调处理
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Object... receivers) {
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                // 标准的权限申请回调，可能不需要加这个双重判断，暂时保留。
                if (PermissionCompat.doubleCheckPermissions(perm)) {
                    granted.add(perm);
                } else {
                    denied.add(perm);
                }
            } else {
                denied.add(perm);
            }
        }

        for (Object receiver : receivers) {
            // 多个权限都获取到，才回调onPermissionsGranted方法
            if (!granted.isEmpty() && denied.isEmpty()) {
                if (receiver instanceof PermissionsCallback) {
                    ((PermissionsCallback) receiver).onPermissionsGranted(requestCode, granted);
                }
            }

            if (!denied.isEmpty()) {
                if (receiver instanceof PermissionsCallback) {
                    ((PermissionsCallback) receiver).onPermissionsDenied(requestCode, denied);
                }
            }
        }
    }

    /**
     * 显示权限设置框
     */
    public static void showTipDialog(Context context, String msg) {

    }
}
