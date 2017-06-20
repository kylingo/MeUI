package com.me.ui.sample;

import android.support.v4.app.Fragment;

import com.me.ui.library.sample.SampleActivity;

public class MainActivity extends SampleActivity {

    @Override
    protected String getSampleTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected Fragment getSampleFragment() {
        return new MainFragment();
    }
}
