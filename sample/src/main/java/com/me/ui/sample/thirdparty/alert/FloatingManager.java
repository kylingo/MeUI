package com.me.ui.sample.thirdparty.alert;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.me.ui.sample.R;
import com.me.ui.sample.SampleApplicationLike;
import com.me.ui.util.ToastUtils;

import java.lang.ref.WeakReference;

/**
 * @author kylingo
 * @since 2019/10/01 16:36
 */
public class FloatingManager {

    private static FloatingManager sInstance = new FloatingManager();

    private WindowManager mWindowManager = null;
    private WindowManager.LayoutParams mWindowLayoutParams = null;
    private WeakReference<View> mFloatingView = null;
    private boolean mIsShow = false;

    public static FloatingManager getInstance() {
        if (sInstance == null) {
            synchronized (FloatingManager.class) {
                if (sInstance == null) {
                    sInstance = new FloatingManager();
                }
            }
        }
        return sInstance;
    }

    private FloatingManager() {
        initWindow();
    }

    private void initWindow() {
        // 获取WindowManager服务
        mWindowManager = (WindowManager) SampleApplicationLike.getContext().getSystemService(Context.WINDOW_SERVICE);

        // 设置LayoutParam
        mWindowLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        mWindowLayoutParams.format = PixelFormat.RGBA_8888;
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mWindowLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.x = 50;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void openFloatingView() {
        Context context = SampleApplicationLike.getContext();
        if (Settings.canDrawOverlays(context)) {
            if (mIsShow) {
                ToastUtils.showShort("Already add floating view !");
                return;
            }

            // 新建悬浮窗控件
            View view = LayoutInflater.from(context).inflate(R.layout.layout_floating_activity, null);
            view.setOnTouchListener(new FloatingOnTouchListener());
            mFloatingView = new WeakReference<>(view);

            // 将悬浮窗控件添加到WindowManager
            mWindowManager.addView(view, mWindowLayoutParams);
            mIsShow = true;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void closeFloatingView() {
        Context context = SampleApplicationLike.getContext();
        if (Settings.canDrawOverlays(context)) {
            ToastUtils.showShort("closeFloatingView mIsShow:" + mIsShow);
            if (mIsShow && mFloatingView != null) {
                View view = mFloatingView.get();
                mWindowManager.removeView(view);
                mIsShow = false;
            }
        }
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();

                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    mWindowLayoutParams.x = mWindowLayoutParams.x + movedX;
                    mWindowLayoutParams.y = mWindowLayoutParams.y + movedY;

                    // 更新悬浮窗控件布局
                    mWindowManager.updateViewLayout(view, mWindowLayoutParams);
                    break;
                default:
                    break;
            }

            return false;
        }
    }
}
