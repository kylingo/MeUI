package com.me.ui.app.wanandroid.page.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

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
 * @since 2019/05/16 16:36
 */
public class WanSearchResultActivity extends BaseListActivity<WanArticleBean.DatasBean> {

    private static final String KEY_DATA = "key_data";
    private String mKeywords;

    public static void launch(Context context, String keywords) {
        Intent intent = new Intent(context, WanSearchResultActivity.class);
        intent.putExtra(KEY_DATA, keywords);
        context.startActivity(intent);
    }

    @Override
    protected void parseIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mKeywords = intent.getStringExtra(KEY_DATA);
        }

        if (TextUtils.isEmpty(mKeywords)) {
            mKeywords = "android";
        }
    }

    @Override
    protected String getTitleName() {
        return mKeywords;
    }

    @Override
    protected BaseAdapter<WanArticleBean.DatasBean> getAdapter() {
        return new WanMainAdapter();
    }

    @Override
    protected void loadData() {
        WanNetEngine.getInstance().getSearch(0, mKeywords)
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
