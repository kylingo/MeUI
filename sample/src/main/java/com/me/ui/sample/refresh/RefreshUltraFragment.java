package com.me.ui.sample.refresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author tangqi on 16-12-30.
 *         Ultra: https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh
 */
public class RefreshUltraFragment extends BaseFragment {

    private PtrFrameLayout mPtrFrameLayout;
    private ArrayAdapter<String> mArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_refresh_ultra, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mPtrFrameLayout = (PtrFrameLayout) rootView.findViewById(R.id.ptr_frame_ultra);

        ListView listView = (ListView) rootView.findViewById(R.id.lv_ultra);
        mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getData());
        listView.setAdapter(mArrayAdapter);

        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getContext());
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrameLayout.refreshComplete();
                    }
                }, 2000);
            }
        });
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
}
