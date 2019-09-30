package com.me.ui.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.me.ui.library.sample.SampleActivity;
import com.me.ui.sample.library.util.ThemeUtils;
import com.me.ui.util.ToastUtils;

import java.util.List;

public class SampleMainActivity extends SampleActivity {

    private long mLastExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.setTheme(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getSampleTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected Fragment getSampleFragment() {
        return new MainFragment();
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (isFragmentStackEmpty() && currentTime - mLastExitTime > 2000) {
            mLastExitTime = currentTime;
            ToastUtils.showShort("再按一次退出");
        } else {
            super.onBackPressed();
        }
    }

    private boolean isFragmentStackEmpty() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        return fragments.size() == 0;
    }
}
