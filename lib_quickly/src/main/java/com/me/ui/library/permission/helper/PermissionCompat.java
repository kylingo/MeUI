package com.me.ui.library.permission.helper;

import android.Manifest;

import com.me.ui.library.permission.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限兼容处理类
 *
 * @author kylingo on 18/8/30
 */
public class PermissionCompat {

    /**
     * 兼容6.0以下机型权限申请
     * 范围：录音，相机
     *
     * @see #checkFakePermissions(Object, int, String...)
     */
    @Deprecated
    public static boolean checkLowPermissions(Object receiver, int requestCode, String... perms) {
        List<String> denied = new ArrayList<>();
        for (String perm : perms) {
            if (Manifest.permission.CAMERA.equals(perm)) {
                if (!checkCameraPermission()) {
                    denied.add(perm);
                }
            } else if (Manifest.permission.RECORD_AUDIO.equals(perm)) {
                if (!checkRecordPermission()) {
                    denied.add(perm);
                }
            }
        }

        boolean result = denied.isEmpty();
        if (!result) {
            // 6.0以下的权限申请，也回调到PermissionsCallback
            if (receiver instanceof PermissionUtils.PermissionsCallback) {
                ((PermissionUtils.PermissionsCallback) receiver).onPermissionsDenied(requestCode, denied);
            }
        }

        return result;
    }

    /**
     * 兼容部分6.0以上手机，检查权限时得到的是假权限问题
     * 范围：录音，相机
     */
    public static boolean checkFakePermissions(Object receiver, int requestCode, String... perms) {
        List<String> denied = new ArrayList<>();
        for (String perm : perms) {
            if (Manifest.permission.CAMERA.equals(perm)) {
                if (!checkCameraPermission()) {
                    denied.add(perm);
                }
            } else if (Manifest.permission.RECORD_AUDIO.equals(perm)) {
                // 保证性能，只检查一次音频权限，有权限一定能录音。待优化，目前录音接口无法保证一定有权限。
                if (!checkRecordPermission()) {
                    denied.add(perm);
                }
            }
        }

        boolean result = denied.isEmpty();
        if (!result) {
            // 如果得到是假权限，也回调到PermissionsCallback
            if (receiver instanceof PermissionUtils.PermissionsCallback) {
                ((PermissionUtils.PermissionsCallback) receiver).onPermissionsDenied(requestCode, denied);
            }
        }

        return result;
    }

    /**
     * 兼容6.0以上机型权限申请
     * 原理：通过标准的方式获取权限后，再调用api检查
     * 范围：录音，相机
     */
    public static boolean doubleCheckPermissions(String perm) {
        if (Manifest.permission.CAMERA.equals(perm)) {
            return checkCameraPermission();
        } else if (Manifest.permission.RECORD_AUDIO.equals(perm)) {
            return checkRecordPermission();
        }

        return true;
    }

    /**
     * 检查相机权限，通过相机接口
     */
    private static boolean checkCameraPermission() {
        return true;
    }

    /**
     * 检查录音权限，通过录音接口
     */
    private static boolean checkRecordPermission() {
        return true;
    }
}
