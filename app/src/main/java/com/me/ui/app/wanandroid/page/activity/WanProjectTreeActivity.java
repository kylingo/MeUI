package com.me.ui.app.wanandroid.page.activity;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseAdapter;
import com.me.ui.app.common.base.BaseListActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanListModule;
import com.me.ui.app.wanandroid.data.WanProjectTreeBean;
import com.me.ui.app.wanandroid.page.adapter.WanProjectTreeAdapter;

/**
 * 项目一级分类
 *
 * @author kylingo
 * @since 2019/05/08 14:29
 */
public class WanProjectTreeActivity extends BaseListActivity<WanProjectTreeBean> {

    @Override
    protected void parseIntent() {

    }

    @Override
    protected String getTitleName() {
        return getString(R.string.wan_project);
    }

    @Override
    protected BaseAdapter<WanProjectTreeBean> getAdapter() {
        return new WanProjectTreeAdapter();
    }

    @Override
    protected void loadData() {
        WanNetEngine.getInstance().getProjectTree()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanProjectTreeBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        onRefreshFailure();
                    }

                    @Override
                    public void onNext(WanListModule<WanProjectTreeBean> wanNavigationBeanWanListModule) {
                        if (wanNavigationBeanWanListModule != null) {
                            onRefreshUI(wanNavigationBeanWanListModule.getData());
                        } else {
                            onRefreshUI(null);
                        }
                    }
                });
    }
}
