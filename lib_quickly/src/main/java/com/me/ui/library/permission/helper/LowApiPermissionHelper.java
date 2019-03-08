package com.me.ui.library.permission.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * @author kylingo on 18/8/30
 */
public class LowApiPermissionHelper<T> extends PermissionHelper<T> {

    LowApiPermissionHelper(T host) {
        super(host);
    }

    @Override
    public Context getContext() {
        if (getHost() instanceof Activity) {
            return (Context) getHost();
        } else if (getHost() instanceof Fragment) {
            return ((Fragment) getHost()).getContext();
        } else if (getHost() instanceof android.app.Fragment) {
            return ((android.app.Fragment) getHost()).getActivity();
        } else {
            throw new IllegalStateException("Unknown host: " + getHost());
        }
    }

    @Override
    public void directRequestPermissions(int requestCode, String... perms) {
        throw new IllegalStateException("Should never be requesting permissions on API < 23");
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String perm) {
        return false;
    }

    @Override
    public void showRequestPermissionRationale(String rationale, int requestCode, String... perms) {
        throw new IllegalStateException("Should never be requesting permissions on API < 23");
    }
}
