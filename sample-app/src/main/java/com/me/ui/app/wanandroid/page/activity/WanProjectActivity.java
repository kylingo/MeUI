package com.me.ui.app.wanandroid.page.activity;

import android.content.Context;
import android.content.Intent;

import com.me.ui.app.common.base.BaseAdapter;
import com.me.ui.app.common.base.BaseListActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanArticleBean;
import com.me.ui.app.wanandroid.data.WanModule;
import com.me.ui.app.wanandroid.data.WanProjectTreeBean;
import com.me.ui.app.wanandroid.page.adapter.WanMainAdapter;

/**
 * 项目二级分类
 *
 * @author kylingo
 * @since 2019/05/08 15:08
 */
public class WanProjectActivity extends BaseListActivity<WanArticleBean.DatasBean> {

    private static final String KEY_DATA = "key_data";
    private WanProjectTreeBean mWanProjectTreeBean;
    private int mCid;
    private String mTitle;

    public static void launch(Context context, WanProjectTreeBean wanProjectTreeBean) {
        Intent intent = new Intent(context, WanProjectActivity.class);
        intent.putExtra(KEY_DATA, wanProjectTreeBean);
        context.startActivity(intent);
    }

    @Override
    protected void parseIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mWanProjectTreeBean = (WanProjectTreeBean) intent.getSerializableExtra(KEY_DATA);
            mCid = mWanProjectTreeBean.getId();
            mTitle = mWanProjectTreeBean.getName();
        } else {
            mCid = 294;
        }
    }

    @Override
    protected String getTitleName() {
        return mTitle;
    }

    @Override
    protected BaseAdapter<WanArticleBean.DatasBean> getAdapter() {
        return new WanMainAdapter();
    }

    @Override
    protected void loadData() {
        WanNetEngine.getInstance().getProjectCategory(0, mCid)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        onRefreshFailure();
                    }

                    @Override
                    public void onNext(WanModule<WanArticleBean> wanNavigationBeanWanListModule) {
                        if (wanNavigationBeanWanListModule != null) {
                            onRefreshUI(wanNavigationBeanWanListModule.getData().getDatas());
                        } else {
                            onRefreshUI(null);
                        }
                    }
                });
    }
}
