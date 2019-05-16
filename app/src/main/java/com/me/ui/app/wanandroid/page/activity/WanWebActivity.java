package com.me.ui.app.wanandroid.page.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanCommonBean;
import com.me.ui.app.wanandroid.data.WanModule;
import com.me.ui.app.wanandroid.page.view.WanTitleView;
import com.me.ui.util.ToastUtils;

/**
 * @author kylingo
 * @since 2019/04/28 12:27
 */
public class WanWebActivity extends BaseActivity {

    private static final int MIN_FONT_SIZE = 8;
    private static final int MIN_LOGICAL_FONT_SIZE = 8;
    private static final int DEFAULT_FONT_SIZE = 16;
    private static final int DEFAULT_FIXED_FONT_SIZE = 13;

    protected WebView mWebView;
    protected ProgressBar mProgressBar;
    protected String mTitle;
    protected String mUrl;
    protected int mId;
    protected boolean mIsCollect;

    public static void launch(Context context, String title, String url, int id, boolean isCollect) {
        Intent intent = new Intent(context, WanWebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("id", id);
        intent.putExtra("isCollect", isCollect);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wan_web;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    protected void initData() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");
        mId = intent.getIntExtra("id", 0);
        mIsCollect = intent.getBooleanExtra("isCollect", false);
    }

    protected void initView() {
        WanTitleView wanTitleView = findViewById(R.id.view_wan_web_title);
        wanTitleView.setTitle(mTitle);
        wanTitleView.setRightResource(R.mipmap.ic_search);
        wanTitleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCollect();
            }
        });

        mProgressBar = findViewById(R.id.pb_web_load);
        mWebView = findViewById(R.id.wv_web);
        initSettings();
        loadUrl(mUrl);

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

    protected void initSettings() {
        WebSettings webSettings = mWebView.getSettings();
        if (webSettings != null) {
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

    private void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    private void onClickCollect() {
        if (mIsCollect) {
            unCollectArticle();
        } else {
            collectArticle();
        }
    }

    private void unCollectArticle() {
        WanNetEngine.getInstance().postUnCollectArticle(mId)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanCommonBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("取消收藏失败");
                    }

                    @Override
                    public void onNext(WanModule<WanCommonBean> wanCommonBeanWanModule) {
                        mIsCollect = false;
                        ToastUtils.showShort("取消收藏成功");
                    }

                });
    }

    private void collectArticle() {
        WanNetEngine.getInstance().postCollectArticle(mId)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanCommonBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("收藏失败");
                    }

                    @Override
                    public void onNext(WanModule<WanCommonBean> wanCommonBeanWanModule) {
                        mIsCollect = true;
                        ToastUtils.showShort("收藏成功");
                    }

                });
    }
}
