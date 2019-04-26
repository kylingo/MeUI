package com.me.ui.app.wanandroid.page.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseFragment;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanArticleBean;
import com.me.ui.app.wanandroid.data.WanModule;
import com.me.ui.app.wanandroid.page.adapter.WanMainAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * @author tangqi
 * @since 2019/04/26 23:43
 */
public class WanMainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager mLayoutManager;

    private WanMainAdapter mAdapter;

    @BindView(R.id.srl_wan_main)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_wan_main)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_wan_main;
    }

    @Override
    protected void initView(View view) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary);
        mAdapter = new WanMainAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        loadData();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void loadData() {
        WanNetEngine.getInstance().getMainArticleList(0)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(WanModule<WanArticleBean> wanModule) {
                        if (wanModule != null) {
                            WanArticleBean wanArticleBean = wanModule.getData();
                            if (wanArticleBean != null) {
                                List<WanArticleBean.DatasBean> datasBeans = wanArticleBean.getDatas();
                                if (datasBeans != null) {
                                    mAdapter.setData(datasBeans);
                                }
                            }
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
