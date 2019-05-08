package com.me.ui.app.wanandroid.page.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import com.me.ui.app.wanandroid.page.adapter.WanNavigationAdapter;
import com.me.ui.app.wanandroid.page.view.WanTitleView;

import butterknife.BindView;

/**
 * @author kylingo
 * @since 2019/05/07 17:34
 */
public class WanNavigationActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager mLayoutManager;
    private WanNavigationAdapter mAdapter;

    @BindView(R.id.view_wan_navigation_title)
    WanTitleView mWanTitleView;

    @BindView(R.id.srl_wan_navigation)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_wan_navigation)
    RecyclerView mRecyclerView;

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
        mWanTitleView.setTitle(getString(R.string.wan_navigation));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary);
        mAdapter = new WanNavigationAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void initData() {
        loadData();
        mSwipeRefreshLayout.setRefreshing(true);

        WanNetEngine.getInstance().getProjectCategory(0, 294)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {

                        }
                    }

                    @Override
                    public void onNext(WanModule<WanArticleBean> wanNavigationBeanWanListModule) {
                        if (wanNavigationBeanWanListModule != null) {

                        }
                    }
                });
    }

    public void loadData() {
        WanNetEngine.getInstance().getNavigation()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanNavigationBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {

                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(WanListModule<WanNavigationBean> wanNavigationBeanWanListModule) {
                        if (wanNavigationBeanWanListModule != null) {
                            mAdapter.setData(wanNavigationBeanWanListModule.getData());
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
