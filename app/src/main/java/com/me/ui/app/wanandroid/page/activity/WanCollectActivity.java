package com.me.ui.app.wanandroid.page.activity;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseAdapter;
import com.me.ui.app.common.base.BaseListActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanArticleBean;
import com.me.ui.app.wanandroid.data.WanModule;
import com.me.ui.app.wanandroid.page.adapter.WanMainAdapter;

/**
 * @author kylingo
 * @since 2019/05/10 17:35
 */
public class WanCollectActivity extends BaseListActivity<WanArticleBean.DatasBean> {

    @Override
    protected void parseIntent() {

    }

    @Override
    protected String getTitleName() {
        return getString(R.string.wan_mine_collect);
    }

    @Override
    protected BaseAdapter<WanArticleBean.DatasBean> getAdapter() {
        return new WanMainAdapter();
    }

    @Override
    protected void loadData() {
        WanNetEngine.getInstance().getCollectArticleList(0)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        onRefreshFailure();
                    }

                    @Override
                    public void onNext(WanModule<WanArticleBean> wanArticleBeanWanModule) {
                        if (wanArticleBeanWanModule != null && wanArticleBeanWanModule.getData() != null) {
                            onRefreshUI(wanArticleBeanWanModule.getData().getDatas());
                        } else {
                            onRefreshUI(null);
                        }
                    }
                });

    }
}
