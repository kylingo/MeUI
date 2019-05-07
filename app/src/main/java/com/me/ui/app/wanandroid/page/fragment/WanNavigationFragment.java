package com.me.ui.app.wanandroid.page.fragment;

import android.view.View;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseFragment;
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
public class WanNavigationFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_wan_navigation;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
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
