package com.me.ui.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.me.ui.library.sample.SampleActivity;
import com.me.ui.sample.library.util.ThemeUtils;
import com.me.ui.util.LogUtils;
import com.me.ui.util.ToastUtils;

import java.util.List;

public class SampleMainActivity extends SampleActivity {

    private static final String TAG = SampleMainActivity.class.getSimpleName();
    private long mLastExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.setTheme(this);
//        hookLayoutInflater();
        super.onCreate(savedInstanceState);

    }

    private void hookLayoutInflater() {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                long start = System.currentTimeMillis();
                View view = getDelegate().createView(parent, name, context, attrs);
                long cost = System.currentTimeMillis() - start;
                LogUtils.d(TAG, "hookLayoutInflater onCreateView:" + cost);
                return view;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                long start = System.currentTimeMillis();
                View view = getDelegate().createView(null, name, context, attrs);
                long cost = System.currentTimeMillis() - start;
                LogUtils.d(TAG, "hookLayoutInflater onCreateView2:" + cost);
                return view;
            }
        });
    }

    @Override
    protected String getSampleTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected Fragment getSampleFragment() {
        return new SampleMainFragment();
    }

    @Override
    public void onBackPressed() {
        if (!isFragmentStackEmpty()) {
            super.onBackPressed();
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastExitTime > 2000) {
            mLastExitTime = currentTime;
            ToastUtils.showShort("再按一次退出");
        } else {
            super.onBackPressed();
        }
    }

    private boolean isFragmentStackEmpty() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        int fragmentSize = fragments.size();
        if (fragmentSize == 1) {
            Fragment fragment = fragments.get(0);
            if (fragment instanceof SampleMainFragment) {
                return true;
            }
        }
        return fragments.size() == 0;
    }
}
