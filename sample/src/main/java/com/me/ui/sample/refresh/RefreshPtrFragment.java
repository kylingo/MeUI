package com.me.ui.sample.refresh;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.me.ui.refresh.PullToRefreshBase;
import com.me.ui.refresh.PullToRefreshListView;
import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangqi on 16-12-29.
 */

public class RefreshPtrFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2 {

    private static final int DELAY_REFRESH = 1000;

    private PullToRefreshListView mPullToRefreshListView;
    private ArrayAdapter<String> mArrayAdapter;
    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refresh, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initData();
    }

    private void initData() {
        mHandler = new Handler();
    }

    @SuppressWarnings("unchecked")
    private void initView(View view) {
        mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.lv_refresh);
        mPullToRefreshListView.setOnRefreshListener(this);
        ListView mListView = mPullToRefreshListView.getRefreshableView();

        mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getData());
        mListView.setAdapter(mArrayAdapter);
    }

    private List<String> getData() {
        List<String> dataList = new ArrayList<>();
        int listSize = 20;

        int offset = mArrayAdapter != null ? mArrayAdapter.getCount() : 0;
        for (int i = offset; i < listSize + offset; i++) {
            dataList.add("item" + i);
        }

        return dataList;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mArrayAdapter.addAll(getData());
                mArrayAdapter.notifyDataSetChanged();

                mPullToRefreshListView.onRefreshComplete();
            }
        }, DELAY_REFRESH);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }
}
