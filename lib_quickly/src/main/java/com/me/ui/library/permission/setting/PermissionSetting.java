package com.me.ui.library.permission.setting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * 跳转权限设置
 *
 * @author kylingo on 18/8/30
 */
public class PermissionSetting {

    private static final String MARK = Build.MANUFACTURER.toLowerCase();

    public static void gotoPermissionSetting(Context context) {
        try {
            Intent intent = obtainSettingIntent(context);
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                // Fix bugly #575754 ActivityNotFoundException
                Intent defaultIntent = defaultApi(context);
                context.startActivity(defaultIntent);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private static Intent obtainSettingIntent(Context context) {
        if (MARK.contains("huawei")) {
            return huaweiApi(context);
        } else if (MARK.contains("xiaomi")) {
            return xiaomiApi(context);
        } else if (MARK.contains("oppo")) {
            return oppoApi(context);
        } else if (MARK.contains("vivo")) {
            return vivoApi(context);
        } else if (MARK.contains("samsung")) {
            return samsungApi(context);
        } else if (MARK.contains("meizu")) {
            return meizuApi(context);
        } else if (MARK.contains("smartisan")) {
            return smartisanApi(context);
        }
        return defaultApi(context);
    }

    /**
     * App details page.
     */
    private static Intent defaultApi(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }

    /**
     * Huawei cell phone Api23 the following method.
     */
    private static Intent huaweiApi(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return defaultApi(context);
        }
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName("com.huawei.systemmanager",
                "com.huawei.permissionmanager.ui.MainActivity"));
        return intent;
    }

    /**
     * Xiaomi phone to achieve the method.
     */
    private static Intent xiaomiApi(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("extra_pkgname", context.getPackageName());
        context.startActivity(intent);
        return intent;
    }

    /**
     * Vivo phone to achieve the method.
     */
    private static Intent vivoApi(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packagename", context.getPackageName());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        intent.setComponent(new ComponentName("com.vivo.permissionmanager",
                "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"));
//        } else {
//            intent.setComponent(new ComponentName("com.iqoo.secure",
//                    "com.iqoo.secure.safeguard.SoftPermissionDetailActivity"));
//        }
        return intent;
    }

    /**
     * Oppo phone to achieve the method.
     */
    private static Intent oppoApi(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", context.getPackageName());
        intent.setComponent(new ComponentName("com.color.safecenter",
                "com.color.safecenter.permission.PermissionManagerActivity"));
        return intent;
    }

    /**
     * Meizu phone to achieve the method.
     */
    private static Intent meizuApi(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            return defaultApi(context);
        }
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", context.getPackageName());
        intent.setComponent(new ComponentName("com.meizu.safe",
                "com.meizu.safe.security.AppSecActivity"));
        return intent;
    }

    /**
     * Smartisan phone to achieve the method.
     */
    private static Intent smartisanApi(Context context) {
        return defaultApi(context);
    }

    /**
     * Samsung phone to achieve the method.
     */
    private static Intent samsungApi(Context context) {
        return defaultApi(context);
    }
}
