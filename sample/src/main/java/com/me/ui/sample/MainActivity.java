package com.me.ui.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.me.ui.library.sample.SampleActivity;
import com.me.ui.sample.support.util.ThemeUtils;

public class MainActivity extends SampleActivity {

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
}
