package com.me.ui.app.wanandroid.page.activity;

import android.os.Bundle;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanArticleBean;
import com.me.ui.app.wanandroid.data.WanListModule;
import com.me.ui.app.wanandroid.data.WanModule;
import com.me.ui.app.wanandroid.data.WanNavigationBean;
import com.me.ui.app.wanandroid.data.WanProjectTreeBean;

/**
 * @author kylingo
 * @since 2019/05/07 17:34
 */
public class WanNavigationActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wan_navigation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    protected void initView() {

    }

    protected void initData() {
        WanNetEngine.getInstance().getNavigation()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanNavigationBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {

                        }
                    }

                    @Override
                    public void onNext(WanListModule<WanNavigationBean> wanNavigationBeanWanListModule) {
                        if (wanNavigationBeanWanListModule != null) {

                        }
                    }
                });

        WanNetEngine.getInstance().getProjectTree()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanProjectTreeBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {

                        }
                    }

                    @Override
                    public void onNext(WanListModule<WanProjectTreeBean> wanNavigationBeanWanListModule) {
                        if (wanNavigationBeanWanListModule != null) {

                        }
                    }
                });

        WanNetEngine.getInstance().getProjectCategory(0, 294)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {

                        }
                    }

                    @Override
                    public void onNext(WanModule<WanArticleBean>wanNavigationBeanWanListModule) {
                        if (wanNavigationBeanWanListModule != null) {

                        }
                    }
                });

    }
}
