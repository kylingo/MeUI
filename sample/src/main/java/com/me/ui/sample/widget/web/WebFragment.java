package com.me.ui.sample.widget.web;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseMenuFragment;

/**
 * @author studiotang on 17/9/19
 */
public class WebFragment extends BaseMenuFragment {
    private static final String DEFAULT_URL = "https://kylingo.github.io";

    private static final int MIN_FONT_SIZE = 8;
    private static final int MIN_LOGICAL_FONT_SIZE = 8;
    private static final int DEFAULT_FONT_SIZE = 16;
    private static final int DEFAULT_FIXED_FONT_SIZE = 13;

    protected WebView mWebView;
    protected ProgressBar mProgressBar;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_web;
    }

    @Override
    protected void initView(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_web_load);

        mWebView = (WebView) view.findViewById(R.id.wv_web);
        initSettings();
        loadDefaultUrl();

        mWebView.setWebViewClient(new CustomWebViewClient());
        mWebView.setWebChromeClient(new CustomWebChromeClient());
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP &&
                        keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }

                return false;
            }
        });
    }

    @Override
    protected void onMenuClick(MenuItem item) {

    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressBar.setProgress(0);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setProgress(0);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            int maxProgress = mProgressBar.getMax();
            int loadProgress = newProgress * maxProgress / 100;
            int nowProgress = mProgressBar.getProgress();
            if (nowProgress < loadProgress) {
                mProgressBar.setProgress(loadProgress);
            }
        }
    }

    protected void initSettings() {
        WebSettings webSettings = mWebView.getSettings();
        if(webSettings != null) {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadsImagesAutomatically(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDefaultTextEncodingName("utf-8");
            webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
            webSettings.setMinimumFontSize(MIN_FONT_SIZE);
            webSettings.setMinimumLogicalFontSize(MIN_LOGICAL_FONT_SIZE);
            webSettings.setDefaultFontSize(DEFAULT_FONT_SIZE);
            webSettings.setDefaultFixedFontSize(DEFAULT_FIXED_FONT_SIZE);
            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            webSettings.setLightTouchEnabled(false);
            webSettings.setSaveFormData(true);
            webSettings.setSavePassword(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setNeedInitialFocus(false);
            webSettings.setSupportMultipleWindows(false);
            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setDatabaseEnabled(true);
            if (Build.VERSION.SDK_INT >= 21) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptThirdPartyCookies(mWebView, true);
            }
        }
    }

    protected void loadDefaultUrl() {
        loadUrl(getDefaultUrl());
    }

    private void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    protected String getDefaultUrl() {
        return DEFAULT_URL;
    }

}
