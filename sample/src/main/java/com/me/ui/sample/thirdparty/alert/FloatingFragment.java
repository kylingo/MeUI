package com.me.ui.sample.thirdparty.alert;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.sample.SampleApplicationLike;
import com.me.ui.util.ToastUtils;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/10/01 15:56
 * 参考文献：
 * 1、@link{https://blog.csdn.net/dongzhong1990/article/details/80512706}
 * 2、悬浮窗权限问题
 * 3、辅助权限，AccessibilityService
 */
public class FloatingFragment extends SampleFragment<String> {

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

    @Override
    protected void addItems(List<String> items) {
        items.add("开启服务");
        items.add("开启");
        items.add("关闭");
    }

    @Override
    protected void onClickItem(String item) {
        if (TextUtils.equals(item, "开启服务")) {
            openAccessibility();
        } else if (TextUtils.equals(item, "开启")) {
            checkPermission();
        } else if (TextUtils.equals(item, "关闭")) {
            closeFloatingView();
        }
    }

    protected void openAccessibility() {
        if (getActivity() != null) {
            AccessibilityServiceHelper.goServiceSettings(getActivity());
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void checkPermission() {
        if (canDrawOverlays()) {
            openFloatingView();
        } else {
            ToastUtils.showShort("checkPermission failure");
            String packageName = SampleApplicationLike.getContext().getPackageName();
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + packageName));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected boolean canDrawOverlays() {
        return Settings.canDrawOverlays(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (canDrawOverlays()) {
                openFloatingView();
            } else {
                ToastUtils.showShort("checkPermission failure");
            }
        }
    }

    protected void openFloatingView() {
        FloatingManager.getInstance().openFloatingView();
    }

    protected void closeFloatingView() {
        FloatingManager.getInstance().closeFloatingView();
    }
}
