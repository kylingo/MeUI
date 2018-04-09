package com.me.ui.sample.widget.refresh;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.ui.sample.BaseFragment;
import com.me.ui.sample.R;
import com.me.ui.sample.base.RefreshAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kylingo on 18/4/8
 *         https://github.com/scwang90/SmartRefreshLayout
 */
public class RefreshSmartFragment extends BaseFragment {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private RefreshAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_refresh_smart;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_smart);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new RefreshAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout = view.findViewById(R.id.fl_smart);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setItems(getData());

                        mRefreshLayout.finishRefresh();
//                        mRefreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                    }
                }, 1000);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addItems(getData());
                        mRefreshLayout.finishLoadMore();
//                        mRefreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                    }
                }, 1000);
            }
        });

        mRefreshLayout.autoRefresh();
    }

    private List<String> getData() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            items.add(String.valueOf(i));
        }
        return items;
    }
}
