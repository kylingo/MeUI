package com.me.ui.sample.thirdparty.arouter.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.me.ui.sample.base.BaseActivity;

/**
 * @author kylingo on 18/10/10
 */
public class SchemeFilterActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        if (uri != null) {
            ARouter.getInstance().build(uri).navigation();
        }

        // Finish activity can add navigation callback
        finish();
    }
}
