package com.me.ui.sample.widget.web;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.me.ui.library.sample.AbstractSampleFragment;
import com.me.ui.sample.R;

/**
 * @author studiotang on 17/9/19
 */
public class WebFragment extends AbstractSampleFragment {
    private static final int DELAY_DIFF = 2000;
    private static final String DEFAULT_URL = "https://kylingo.github.io";
    private long mLastExitTime;

    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_web;
    }

    @Override
    protected void initView(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_web_load);

        mWebView = (WebView) view.findViewById(R.id.wv_web);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setJavaScriptEnabled(true);
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

    ;

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
