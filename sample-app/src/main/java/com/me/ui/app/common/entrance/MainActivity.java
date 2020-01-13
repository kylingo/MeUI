package com.me.ui.app.common.entrance;

import android.support.v4.app.Fragment;

import com.me.ui.app.R;
import com.me.ui.library.sample.SampleActivity;

/**
 * @author kylingo
 * @since 2019/04/22 14:57
 */
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
