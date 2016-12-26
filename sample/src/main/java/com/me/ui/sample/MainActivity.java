package com.me.ui.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.me.ui.refresh.PullToRefreshBase;
import com.me.ui.refresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PullToRefreshBase.OnRefreshListener2 {

    private static final int DELAY_REFRESH = 1000;

    private PullToRefreshListView mPullToRefreshListView;
    private ArrayAdapter<String> mArrayAdapter;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        mHandler = new Handler();
    }

    private void initView() {
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_main);
        mPullToRefreshListView.setOnRefreshListener(this);
        ListView mListView = mPullToRefreshListView.getRefreshableView();

        mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getData());
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
