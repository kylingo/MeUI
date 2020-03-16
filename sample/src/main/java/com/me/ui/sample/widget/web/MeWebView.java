package com.me.ui.sample.widget.web;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author kylingo
 * @since 2020/03/16 10:40
 */
public class MeWebView extends WebView {

    private static final int MIN_FONT_SIZE = 8;
    private static final int MIN_LOGICAL_FONT_SIZE = 8;
    private static final int DEFAULT_FONT_SIZE = 16;
    private static final int DEFAULT_FIXED_FONT_SIZE = 13;

    private WebViewClient mWebViewClient;
    private WebChromeClient mWebChromeClient;

    public MeWebView(Context context) {
        super(context);
        init();
    }

    public MeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initSettings();
        initListener();
    }

    protected void initSettings() {
        WebSettings webSettings = getSettings();
        if (webSettings != null) {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadsImagesAutomatically(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDefaultTextEncodingName("utf-8");
            webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
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

            webSettings.setMinimumFontSize(MIN_FONT_SIZE);
            webSettings.setMinimumLogicalFontSize(MIN_LOGICAL_FONT_SIZE);
            webSettings.setDefaultFontSize(DEFAULT_FONT_SIZE);
            webSettings.setDefaultFixedFontSize(DEFAULT_FIXED_FONT_SIZE);

            if (Build.VERSION.SDK_INT >= 21) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptThirdPartyCookies(this, true);
            }
        }
    }

    private void initListener() {
        setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP &&
                        keyCode == KeyEvent.KEYCODE_BACK) {
                    if (canGoBack()) {
                        goBack();
                        return true;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
        mWebViewClient = client;
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        super.setWebChromeClient(client);
        mWebChromeClient = client;
    }
}
