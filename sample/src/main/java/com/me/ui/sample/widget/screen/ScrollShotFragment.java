package com.me.ui.sample.widget.screen;

import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.me.ui.library.helper.CacheHelper;
import com.me.ui.library.helper.ScreenShotHelper;
import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseMenuFragment;

/**
 * @author kylingo on 18/6/22
 */
public class ScrollShotFragment extends BaseMenuFragment {

    private ScrollView mScrollView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_scroll_view;
    }

    @Override
    protected void initView(View view) {
        mScrollView = view.findViewById(R.id.sv_common);
    }

    private void getScreenShot(ScrollView scrollView) {
        String filePath = CacheHelper.getScreenShotPath(mContext, "scroll");
        ScreenShotHelper.createScreenShot(scrollView, filePath);
    }

    @Override
    protected void onMenuClick(MenuItem item) {
        getScreenShot(mScrollView);
    }
}
