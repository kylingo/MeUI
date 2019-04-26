package com.me.ui.app.wanandroid.page.activity;

import android.os.Bundle;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.data.WanArticleBean;
import com.me.ui.app.wanandroid.data.WanBannerBean;
import com.me.ui.app.wanandroid.data.WanHotKeyBean;
import com.me.ui.app.wanandroid.data.WanListModule;
import com.me.ui.app.wanandroid.data.WanModule;
import com.me.ui.app.wanandroid.data.WanWebsiteBean;
import com.me.ui.app.wanandroid.api.WanNetEngine;

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

        WanNetEngine.getInstance().getMainArticleList(0)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(WanModule<WanArticleBean> wanModule) {
                        if (wanModule != null) {

                        }
                    }
                });

        WanNetEngine.getInstance().getMainBanner()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanBannerBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(WanListModule<WanBannerBean> wanBannerBeanWanListModule) {
                        if (wanBannerBeanWanListModule != null) {

                        }
                    }
                });

        WanNetEngine.getInstance().getMainWebsite()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanWebsiteBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(WanListModule<WanWebsiteBean> wanWebsiteBeanWanListModule) {
                        if (wanWebsiteBeanWanListModule != null) {

                        }
                    }
                });

        WanNetEngine.getInstance().getMainHotKey()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanHotKeyBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(WanListModule<WanHotKeyBean> wanHotKeyBeanWanListModule) {
                        if (wanHotKeyBeanWanListModule != null) {

                        }
                    }
                });
    }
}
