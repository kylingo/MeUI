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
import com.me.ui.app.wanandroid.data.WanTreeBean;
import com.me.ui.app.wanandroid.page.adapter.WanMainAdapter;

import java.util.List;

/**
 * 体系二级分类页面
 *
 * @author kylingo
 * @since 2019/05/07 14:29
 */
public class WanTreeCategoryActivity extends BaseListActivity<WanArticleBean.DatasBean> {

    private static final String TREE_BEAN = "tree_bean";
    private WanTreeBean mWanTreeBean;

    public static void launch(Context context, WanTreeBean wanTreeBean) {
        Intent intent = new Intent(context, WanTreeCategoryActivity.class);
        intent.putExtra(TREE_BEAN, wanTreeBean);
        context.startActivity(intent);
    }

    @Override
    protected void parseIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mWanTreeBean = (WanTreeBean) intent.getSerializableExtra(TREE_BEAN);
        }
    }

    @Override
    protected String getTitleName() {
        if (mWanTreeBean != null) {
            return mWanTreeBean.getName();
        }
        return null;
    }

    @Override
    protected BaseAdapter<WanArticleBean.DatasBean> getAdapter() {
        return new WanMainAdapter();
    }

    @Override
    protected void loadData() {
        int cid = 60;
        if (mWanTreeBean != null) {
            List<WanTreeBean> wanTreeBeans = mWanTreeBean.getChildren();
            if (wanTreeBeans != null && wanTreeBeans.size() > 0) {
                WanTreeBean curWanTreeBean = wanTreeBeans.get(0);
                cid = curWanTreeBean.getId();
            }
        }
        WanNetEngine.getInstance().getTreeCategory(0, cid)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        onRefreshFailure();
                    }

                    @Override
                    public void onNext(WanModule<WanArticleBean> wanModule) {
                        if (wanModule != null) {
                            WanArticleBean wanArticleBean = wanModule.getData();
                            if (wanArticleBean != null) {
                                List<WanArticleBean.DatasBean> datasBeans = wanArticleBean.getDatas();
                                if (datasBeans != null) {
                                    onRefreshUI(datasBeans);
                                    return;
                                }
                            }
                        }
                        onRefreshFailure();
                    }
                });
    }
}
