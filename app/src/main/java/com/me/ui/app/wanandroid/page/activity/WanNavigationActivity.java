package com.me.ui.app.wanandroid.page.activity;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseAdapter;
import com.me.ui.app.common.base.BaseListActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanListModule;
import com.me.ui.app.wanandroid.data.WanNavigationBean;
import com.me.ui.app.wanandroid.page.adapter.WanNavigationAdapter;

/**
 * 导航
 *
 * @author kylingo
 * @since 2019/05/07 17:34
 */
public class WanNavigationActivity extends BaseListActivity<WanNavigationBean> {

    @Override
    protected void parseIntent() {

    }

    @Override
    protected String getTitleName() {
        return getString(R.string.wan_navigation);
    }

    @Override
    protected BaseAdapter<WanNavigationBean> getAdapter() {
        return new WanNavigationAdapter();
    }

    public void loadData() {
        WanNetEngine.getInstance().getNavigation()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanNavigationBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        onRefreshFailure();
                    }

                    @Override
                    public void onNext(WanListModule<WanNavigationBean> wanNavigationBeanWanListModule) {
                        if (wanNavigationBeanWanListModule != null) {
                            onRefreshUI(wanNavigationBeanWanListModule.getData());
                        } else {
                            onRefreshFailure();
                        }
                    }
                });
    }
}
