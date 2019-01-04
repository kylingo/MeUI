package com.me.ui.sample.library.basic;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.sample.library.util.CopyUtils;
import com.me.ui.sample.library.util.UriCompat;

import java.io.File;
import java.util.List;

/**
 * @author kylingo
 * @since 2018/12/19 12:58
 */
public class UpgradeFragment extends SampleFragment<String> {

    private static final int REQUEST_CODE_UNKNOWN_APP = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 测试时将apk拷贝到assets目录，sample-debug.apk
        CopyUtils.assetsCopy(getActivity(), "apk", CopyUtils.getCachePath(getActivity()));
    }

    @Override
    protected void addItems(List<String> items) {
        items.add("install");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "install":
                checkPermission();
                break;
        }
    }

    /**
     * Android 8.0：Requesting uid 10168 needs to declare permission android.permission.REQUEST_INSTALL_PACKAGES
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 此处可能不需要检查权限，由系统自己去控制
            boolean hasInstallPermission = getActivity().getPackageManager().canRequestPackageInstalls();
            if (hasInstallPermission) {
                installApk();
            } else {
                // 跳转至“安装未知应用”权限界面，引导用户开启权限
                Uri selfPackageUri = Uri.parse("package:" + getActivity().getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, selfPackageUri);
                startActivityForResult(intent, REQUEST_CODE_UNKNOWN_APP);
            }
        } else {
            installApk();
        }
    }

    private void installApk() {
        File file = new File(CopyUtils.getCachePath(getActivity()), "sample-debug.apk");

        // 使用兼容类
        Uri uri = UriCompat.getUriForFile(getActivity(), file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        UriCompat.addUriPermission(intent);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UNKNOWN_APP) {
            installApk();
        }
    }
}
