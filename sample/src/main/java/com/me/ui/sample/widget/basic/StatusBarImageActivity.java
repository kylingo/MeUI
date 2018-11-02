package com.me.ui.sample.widget.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.me.ui.library.util.StatusBarTranslucentUtils;
import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseActivity;

/**
 * @author tangqi
 * @since 2018/11/02 15:10
 */
public class StatusBarImageActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_image);

        StatusBarTranslucentUtils.setStatusBarTranslucent(this);
        StatusBarTranslucentUtils.setStatusBarDarkMode(this, true);

        int pStatusBarHeight = StatusBarTranslucentUtils.getStatusBarHeight(this);
        View viewPlaceHolder = findViewById(R.id.view_place_holder);
        FrameLayout.LayoutParams holderParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, pStatusBarHeight);
        viewPlaceHolder.setLayoutParams(holderParams);
        viewPlaceHolder.setVisibility(View.VISIBLE);
    }

    @Override
    protected void updateTheme() {

    }
}
