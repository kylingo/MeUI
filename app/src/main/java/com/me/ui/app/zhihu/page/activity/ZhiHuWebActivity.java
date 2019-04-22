package com.me.ui.app.zhihu.page.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseActivity;
import com.me.ui.app.common.view.GlidePaletteListenerImp;
import com.me.ui.app.zhihu.domain.config.ExtraKey;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 知乎日报详情页面
 *
 * @author tangqi
 * @date 2015/10/14 15:13
 */
public class ZhiHuWebActivity extends BaseActivity {

    private String mUrl;
    private String mTitle;
    private GlidePaletteListenerImp mPaletteListenerImp;

    @BindView(R.id.news_header)
    ImageView mNewsHeader;

    @BindView(R.id.img_source)
    TextView mImgSource;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.webView)
    WebView mWebView;

    @BindView(R.id.nsv_content)
    NestedScrollView mNsvContent;

    @BindView(R.id.gson_content)
    CoordinatorLayout mGsonContent;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_webview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getIntent().getStringExtra(ExtraKey.CONTENT_URL);

        initView();
        requestData(mUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.menu_share:
                share(ZhiHuWebActivity.this, mTitle, mUrl);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mPaletteListenerImp = new GlidePaletteListenerImp(mNewsHeader, this, mCollapsingToolbarLayout);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    private void requestData(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> params = parseHtml(url);
                UIAsyncTask uiAsyncTask = new UIAsyncTask();
                uiAsyncTask.execute(params);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 在html+模式下，对获取到的html数据进行修改，去除不必要的数据
     *
     * @param htmlUrl 原始html字符串
     * @return 之后的html数据
     */
    public Map<String, String> parseHtml(String htmlUrl) {
        Map<String, String> htmlMap = new HashMap<>();
        try {
            Document document = Jsoup.connect(htmlUrl).userAgent("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8").get();
            removeElements(document);
            Element header = document.select("div[class=headline]").get(0);
            Elements headerChildren = header.getAllElements();
            for (Element child : headerChildren) {
                if (child.className().equals("headline-title")) {
                    String headline_title = child.text();
                    htmlMap.put("headline_title", headline_title);
                } else if (child.className().equals("img-source")) {
                    String img_source = child.text();
                    htmlMap.put("img_source", img_source);
                } else if (child.nodeName().equals("img")) {
                    String img = child.attr("src");
                    htmlMap.put("img", img);
                }
            }
            header.remove();
            String content = document.outerHtml();
            htmlMap.put("content", content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlMap;
    }

    /**
     * 避免因为要移除的元素不存在，而造成的IndexOutOfBoundsException，先对元素进行判断
     *
     * @param document 从网页解析得到的Document对象
     */
    private void removeElements(Document document) {
        Elements global_header = document.select("div[class=global-header]");
        if (global_header != null && global_header.size() != 0) {
            global_header.remove();
        }
        Elements header_for_mobile = document.select("div[class=header-for-mobile]");
        if (header_for_mobile != null && header_for_mobile.size() != 0) {
            header_for_mobile.remove();
        }
        Elements question = document.select("div[class=question]");
        if (question != null && question.size() == 2) {
            question.get(1).remove();
        }
        Elements qr = document.select("div[class=qr]").remove();
        if (qr != null && qr.size() == 0) {
            qr.remove();
        }
        Elements bottom_wrap = document.select("div[class=bottom-wrap]");
        if (bottom_wrap != null && bottom_wrap.size() != 0) {
            bottom_wrap.remove();
        }
    }

    private class UIAsyncTask extends AsyncTask<Map<String, String>, Map.Entry<String, String>, Void> {

        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(Map<String, String>... params) {
            Map<String, String> map = params[0];
            for (Map.Entry<String, String> entry : map.entrySet()) {
                publishProgress(entry);
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void onProgressUpdate(Map.Entry<String, String>... values) {
            Map.Entry<String, String> entry = values[0];
            if (entry.getKey().equals("headline_title")) {
                mTitle = entry.getValue();
                mCollapsingToolbarLayout.setTitle(entry.getValue());
            } else if (entry.getKey().equals("content")) {
                Log.d("Debug", entry.getValue());
                mWebView.loadDataWithBaseURL(mUrl, entry.getValue(), "text/html", "uft-8", null);
            } else if (entry.getKey().equals("img")) {
                Glide.with(ZhiHuWebActivity.this).load(entry.getValue()).asBitmap().centerCrop().listener(mPaletteListenerImp).into(mNewsHeader);
            } else if (entry.getKey().equals("img_source")) {
                mImgSource.setText(entry.getValue());
                mImgSource.setVisibility(View.VISIBLE);
            }
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // 处理加载进度条
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    public static void share(Context context, String title, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("：\n").append(url);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
