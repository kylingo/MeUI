package com.me.ui.app.wanandroid.page.activity;

import android.os.Bundle;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.network.NetEngine;

/**
 * @author tangqi
 * @since 2019/04/22 23:51
 */
public class WanMainActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wan_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetEngine.getInstance().getMainArticleList(0)
                .compose(RxHelper.<Object>getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
    }
}
