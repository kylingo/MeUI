package com.me.ui.sample.widget.web;

import android.graphics.Bitmap;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseMenuFragment;

/**
 * @author studiotang on 17/9/19
 */
public class WebFragment extends BaseMenuFragment {
    private static final String DEFAULT_URL = "https://kylingo.github.io";

    protected MeWebView mWebView;
    protected ProgressBar mProgressBar;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_web;
    }

    @Override
    protected void initView(View view) {
        mProgressBar = view.findViewById(R.id.pb_web_load);

        mWebView = new MeWebView(getContext());
        FrameLayout flWeb = view.findViewById(R.id.fl_web);
        flWeb.addView(mWebView);

        mWebView.setWebViewClient(new CustomWebViewClient());
        mWebView.setWebChromeClient(new CustomWebChromeClient());

        loadUrl(getDefaultUrl());
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

    private void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    protected String getDefaultUrl() {
        return DEFAULT_URL;
    }

}
