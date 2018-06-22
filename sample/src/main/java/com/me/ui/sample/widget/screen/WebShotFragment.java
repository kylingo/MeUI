package com.me.ui.sample.widget.screen;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.webkit.WebView;

import com.me.ui.library.util.CacheUtils;
import com.me.ui.library.util.ScreenShotUtils;
import com.me.ui.sample.widget.web.WebFragment;

/**
 * @author kylingo on 18/6/22
 */
public class WebShotFragment extends WebFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        enableSlowWholeDocumentDraw();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getDefaultUrl() {
        return "https://www.bilibili.com/";
    }

    @Override
    protected void onMenuClick(MenuItem item) {
        super.onMenuClick(item);
        getScreenShot();
    }

    private void enableSlowWholeDocumentDraw() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();
        }
    }

    private void getScreenShot() {
        String filePath = CacheUtils.getScreenShotPath(mContext, "web");
        ScreenShotUtils.createScreenShot(mWebView, filePath);
    }
}
