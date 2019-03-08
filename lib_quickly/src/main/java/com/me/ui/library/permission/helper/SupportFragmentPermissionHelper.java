package com.me.ui.library.permission.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.me.ui.library.permission.setting.PermissionSetting;


/**
 * @author kylingo on 18/8/30
 */

public class SupportFragmentPermissionHelper extends PermissionHelper<Fragment> {


    SupportFragmentPermissionHelper(Fragment host) {
        super(host);
    }

    @Override
    public Context getContext() {
        return getHost().getActivity();
    }

    @Override
    public void directRequestPermissions(int requestCode, String... perms) {
        try {
            // Fix bugly #571658 IllegalStateException
            getHost().requestPermissions(perms, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String perm) {
        return getHost().shouldShowRequestPermissionRationale(perm);
    }

    @Override
    public void showRequestPermissionRationale(String rationale, int requestCode, String... perms) {
        if (!TextUtils.isEmpty(rationale)) {

        }
    }

    @Override
    public void goAppSetting() {
        PermissionSetting.gotoPermissionSetting(getHost().getContext());
    }
}
