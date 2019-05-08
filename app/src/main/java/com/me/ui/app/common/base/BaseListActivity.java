package com.me.ui.app.common.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.ui.app.R;
import com.me.ui.app.wanandroid.page.view.WanTitleView;

import java.util.List;

import butterknife.BindView;

/**
 * @author kylingo
 * @since 2019/05/08 14:34
 */
public abstract class BaseListActivity<T> extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager mLayoutManager;
    private BaseAdapter<T> mAdapter;

    @BindView(R.id.view_wan_list_title)
    WanTitleView mWanTitleView;

    @BindView(R.id.srl_wan_list)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_wan_list)
    RecyclerView mRecyclerView;

    protected abstract BaseAdapter<T> getAdapter();

    protected abstract void loadData();

    protected void setTitle(String title) {
        mWanTitleView.setTitle(title);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wan_base_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        mAdapter = getAdapter();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary);
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void initData() {
        loadData();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    protected void onRefreshUI(List<T> items) {
        mAdapter.setData(items);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    protected void onRefreshFailure() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
