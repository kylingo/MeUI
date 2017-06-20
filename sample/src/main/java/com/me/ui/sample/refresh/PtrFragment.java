package com.me.ui.sample.refresh;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.me.ui.sample.BaseFragment;
import com.me.ui.sample.R;
import com.me.ui.widget.refresh.PullToRefreshBase;
import com.me.ui.widget.refresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangqi on 16-12-29.
 */

public class PtrFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2 {

    private static final int DELAY_REFRESH = 1000;

    private PullToRefreshListView mPullToRefreshListView;
    private ArrayAdapter<String> mArrayAdapter;
    private Handler mHandler;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_refresh;
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
    @Override
    protected void initView(View view) {
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
