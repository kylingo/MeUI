package com.me.ui.library.permission.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.me.ui.library.permission.setting.PermissionSetting;

import java.util.List;

/**
 * 权限委托类
 * @author kylingo on 18/8/30
 */

public abstract class PermissionHelper<T> {
    private static final String TAG = "PermissionHelper";

    private static final boolean SHOW_RATIONALE = false;

    private T mHost;

    public static PermissionHelper<? extends Activity> newInstance(Activity host) {
        // Android6.0之前需要单独针对机型进行设置
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new LowApiPermissionHelper<>(host);
        }

        if (host instanceof AppCompatActivity) {
            return new AppCompatActivityPermissionHelper((AppCompatActivity) host);
        } else {
            return new ActivityPermissionHelper(host);
        }
    }

    public static PermissionHelper<Fragment> newInstance(Fragment host) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new LowApiPermissionHelper<>(host);
        }

        return new SupportFragmentPermissionHelper(host);
    }

    public static PermissionHelper<android.app.Fragment> newInstance(android.app.Fragment host) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return null;
        }

        return null;
    }

    public PermissionHelper(T host) {
        mHost = host;
    }

    public T getHost() {
        return mHost;
    }

    private boolean shouldShowRationale(String... perms) {
        for (String perm : perms) {
            if (shouldShowRequestPermissionRationale(perm)) {
                return true;
            }
        }
        return false;
    }

    public void requestPermissions(String rationale, int requestCode, String... perms) {
        boolean isShouldShowRationale = shouldShowRationale(perms);
        if (isShouldShowRationale) {
            // 根据产品需求，不再展示权限解释对话框，直接申请权限
            if (SHOW_RATIONALE) {
                showRequestPermissionRationale(rationale, requestCode, perms);
            } else {
                directRequestPermissions(requestCode, perms);
            }
        } else {
            directRequestPermissions(requestCode, perms);
        }
    }

    public boolean somePermissionPermanentlyDenied(List<String> perms) {
        for (String deniedPermission : perms) {
            if (permissionPermanentlyDenied(deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    public boolean permissionPermanentlyDenied(String perm) {
        return !shouldShowRequestPermissionRationale(perm);
    }

    public boolean somePermissionDenied(String... perms) {
        return shouldShowRationale(perms);
    }

    protected void showPermissionApplyDialog(String rationale, int requestCode, String... perms) {

    }

    public abstract Context getContext();

    /**
     * 申请权限
     */
    public abstract void directRequestPermissions(int requestCode, String... perms);

    /**
     * 是否需要展示权限解释
     */
    public abstract boolean shouldShowRequestPermissionRationale(String perm);

    /**
     * 展示权限解释对话框
     */
    public abstract void showRequestPermissionRationale(String rationale, int requestCode, String... perms);

    /**
     * 跳转权限设置
     * 注意：无法兼容的机型，跳转到应用闲情
     */
    public void goAppSetting() {
        PermissionSetting.gotoPermissionSetting(getContext());
    }

}
