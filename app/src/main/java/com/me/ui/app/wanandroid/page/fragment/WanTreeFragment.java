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
import com.me.ui.app.wanandroid.data.WanListModule;
import com.me.ui.app.wanandroid.data.WanTreeBean;
import com.me.ui.app.wanandroid.page.adapter.WanTreeAdapter;
import com.me.ui.app.wanandroid.page.view.WanTitleView;

import java.util.List;

import butterknife.BindView;

/**
 * @author kylingo
 * @since 2019/05/07 13:17
 */
public class WanTreeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager mLayoutManager;
    private WanTreeAdapter mAdapter;

    @BindView(R.id.view_wan_tree_title)
    WanTitleView mWanTitleView;

    @BindView(R.id.srl_wan_tree)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_wan_tree)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_wan_tree;
    }

    @Override
    protected void initView(View view) {
        mWanTitleView.setTitle(getString(R.string.wan_tree));

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary);
        mAdapter = new WanTreeAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        loadData();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void loadData() {
        WanNetEngine.getInstance().getTree()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanTreeBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(WanListModule<WanTreeBean> wanModule) {
                        if (wanModule != null) {
                            List<WanTreeBean> wanTreeBeans = wanModule.getData();
                            mAdapter.setData(wanTreeBeans);
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
