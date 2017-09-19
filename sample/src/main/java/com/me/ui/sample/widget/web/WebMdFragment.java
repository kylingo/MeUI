package com.me.ui.sample.widget.web;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.me.ui.library.sample.AbstractSampleFragment;
import com.me.ui.sample.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author studiotang on 17/9/19
 */
public class WebMdFragment extends AbstractSampleFragment {

    private static final String DEFAULT_URL = "file:///android_asset/web_index.html";
    private WebView mWebView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_web_md;
    }

    @Override
    protected void initView(View view) {
        mWebView = (WebView) view.findViewById(R.id.wv_md_test);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setJavaScriptEnabled(true);
        loadDefaultUrl();

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String data = getMdData();
                if (data != null) {
                    String call = "javascript:marked(\'" + data + "\')";
                    loadUrl(call);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    private void loadDefaultUrl() {
        loadUrl(getDefaultUrl());
    }

    private void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    private String getMdData() {
        StringBuilder sb = new StringBuilder();
        BufferedReader br;
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    getResources().getAssets().open("web_readme.md"));
            br = new BufferedReader(inputReader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\\n");//注意这一行，通常应该是sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private String getDefaultUrl() {
        return DEFAULT_URL;
    }
}
