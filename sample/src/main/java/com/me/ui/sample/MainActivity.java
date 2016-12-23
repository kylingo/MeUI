package com.me.ui.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.me.ui.refresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private PullToRefreshListView mPullToRefreshListView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_main);
        assert mPullToRefreshListView != null;
        mListView = mPullToRefreshListView.getRefreshableView();

        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData()));
    }

    private List<String> getData() {
        List<String> dataList = new ArrayList<>();
        int listSize = 20;

        for (int i = 0; i < listSize; i++) {
            dataList.add("item" + i);
        }

        return dataList;
    }
}
