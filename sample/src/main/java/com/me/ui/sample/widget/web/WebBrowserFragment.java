package com.me.ui.sample.widget.web;

import android.content.Intent;
import android.net.Uri;

import com.me.ui.library.sample.SampleFragment;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/06/25 18:14
 */
public class WebBrowserFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("默认浏览器");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "默认浏览器":
                openDefaultBrowser();
                break;
        }
    }

    private void openDefaultBrowser() {
        String url = "http://noah-pics-1251752892.cosgz.myqcloud.com/20190625/1cbe8ffd023c4a69addb7fb896d98290.jpeg";

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }
}
