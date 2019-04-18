package com.me.ui.library.permission.helper;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;


/**
 * @author kylingo on 18/8/30
 */
public class AppCompatActivityPermissionHelper extends PermissionHelper<AppCompatActivity> {

    AppCompatActivityPermissionHelper(AppCompatActivity host) {
        super(host);
    }

    @Override
    public Context getContext() {
        return getHost();
    }

    @Override
    public void directRequestPermissions(int requestCode, String... perms) {
        ActivityCompat.requestPermissions(getHost(), perms, requestCode);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String perm) {
        return ActivityCompat.shouldShowRequestPermissionRationale(getHost(), perm);
    }

    @Override
    public void showRequestPermissionRationale(String rationale, int requestCode, String... perms) {
        if (!TextUtils.isEmpty(rationale)) {
            showPermissionApplyDialog(rationale, requestCode, perms);
        }
    }
}
