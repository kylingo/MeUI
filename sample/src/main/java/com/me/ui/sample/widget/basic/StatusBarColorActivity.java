package com.me.ui.sample.widget.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.me.ui.library.helper.StatusBarTranslucentHelper;
import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseActivity;

/**
 * @author tangqi
 * @since 2018/11/02 15:06
 */
public class StatusBarColorActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarTranslucentHelper.setStatusBarTranslucent(this);
        StatusBarTranslucentHelper.setStatusBarColor(this, R.color.colorAccent);
    }
}
