package com.me.ui.sample.widget.web;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseMenuFragment;
import com.me.ui.util.ToastUtils;

/**
 * @author studiotang on 17/9/19
 */
public class WebLocalFragment extends BaseMenuFragment {
    private static final String DEFAULT_URL = "file:///android_asset/web/test.html";

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
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);
            mProgressBar.setProgress(0);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            mProgressBar.setProgress(0);
            mProgressBar.setVisibility(View.GONE);

            webView.loadUrl("javascript:document.getElementsByTagName(\"body\")[0].style.backgroundColor=\"#0000FF\";");
//            webView.loadUrl("javascript: alert('Invoke js');");
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

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            ToastUtils.showShort(message);
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            ToastUtils.showShort(message);
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            ToastUtils.showShort(message);
            return super.onJsConfirm(view, url, message, result);
        }
    }

    private void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    protected String getDefaultUrl() {
        return DEFAULT_URL;
    }
}
