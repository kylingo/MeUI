package com.me.ui.sample.thirdparty.alert;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.me.ui.sample.R;
import com.me.ui.sample.SampleApplicationLike;
import com.me.ui.util.BarUtils;
import com.me.ui.util.ProcessUtils;
import com.me.ui.util.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author kylingo
 * @since 2019/10/01 16:36
 */
public class FloatingManager {

    private static FloatingManager sInstance = new FloatingManager();

    private WindowManager mWindowManager = null;
    private WindowManager.LayoutParams mWindowLayoutParams = null;
    private WeakReference<View> mFloatingView = null;
    private WeakReference<TextView> mTvActivity;
    private String mLastTopActivityName = "";
    private boolean mIsShow = false;
    private Timer mTimer;

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
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;
        mWindowLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mWindowLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.y = BarUtils.getStatusBarHeight();
        mWindowLayoutParams.x = 0;
    }

    @TargetApi(Build.VERSION_CODES.M)
    void openFloatingView() {
        Context context = SampleApplicationLike.getContext();
        if (Settings.canDrawOverlays(context)) {
            if (mIsShow) {
                ToastUtils.showShort("Already add floating view !");
                return;
            }

            // 新建悬浮窗控件
            View view = LayoutInflater.from(context).inflate(R.layout.layout_floating_activity, null);
            view.setOnTouchListener(new FloatingOnTouchListener());
            TextView tvActivity = view.findViewById(R.id.tv_floating_activity);
            mTvActivity = new WeakReference<>(tvActivity);
            mFloatingView = new WeakReference<>(view);

            // 将悬浮窗控件添加到WindowManager
            mWindowManager.addView(view, mWindowLayoutParams);
            mIsShow = true;

            if (mTimer != null) {
                mTimer.cancel();
            }
            mTimer = new Timer();
            mTimer.schedule(new RefreshTask(), 0, 1000);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    void closeFloatingView() {
        if (mTimer != null) {
            mTimer.cancel();
        }

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

    public String getLastTopActivityName() {
        return mLastTopActivityName;
    }

    public void setLastTopActivityName(String mLastTopActivityName) {
        this.mLastTopActivityName = mLastTopActivityName;
    }

    public class RefreshTask extends TimerTask {

        @Override
        public void run() {
            if (mIsShow) {
                updateFloatingView();
            } else {
                if (mTimer != null) {
                    mTimer.cancel();
                }
            }
        }
    }

    private void updateFloatingView() {
        Context context = SampleApplicationLike.getContext();
        TextView tvActivity = mTvActivity.get();
        if (tvActivity != null) {
            StringBuilder sb = new StringBuilder();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if (AccessibilityServiceHelper.isAccessibilitySettingsOn(context, AppUtils.getAppPackageName() + "/" + ViewDebugHelperService.class.getName())) {
//                    sb.append(FloatingManager.getInstance().getLastTopActivityName());
//                } else {
//                    sb.append("服务未开，数据不一定准确\n").append(ProcessUtils.getTopActivity(context));
//                }
                String lastTopActivityName = FloatingManager.getInstance().getLastTopActivityName();
                if (TextUtils.isEmpty(lastTopActivityName)) {
                    sb.append("服务未开，数据不一定准确\n");
                    lastTopActivityName = ProcessUtils.getTopActivity(context);
                }
                sb.append(lastTopActivityName);
            } else {
                sb.append(ProcessUtils.getTopActivity(context));
            }

            tvActivity.setText(sb.toString());
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
