package com.me.ui.sample.library.permission;

import android.Manifest;
import android.support.annotation.NonNull;

import com.me.ui.library.permission.PermissionUtils;
import com.me.ui.library.sample.SampleFragment;
import com.me.ui.util.ToastUtils;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/03/08 17:28
 */
public class PermissionFragment extends SampleFragment<String> implements PermissionUtils.PermissionsCallback {

    private String mPermissions = Manifest.permission.READ_PHONE_STATE;

    @Override
    protected void addItems(List<String> items) {
        items.add("电话权限");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "电话权限":
                if (PermissionUtils.checkPermissions(this, "", PermissionUtils.PERMISSION_CODE_READ_PHONE_STATE, mPermissions)) {
                    ToastUtils.showShort("Already has phone state permission");
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == PermissionUtils.PERMISSION_CODE_READ_PHONE_STATE) {
            ToastUtils.showShort("Request phone state permission success");
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PermissionUtils.PERMISSION_CODE_READ_PHONE_STATE) {
            ToastUtils.showShort("Request phone state permission failure");
        }
    }
}
