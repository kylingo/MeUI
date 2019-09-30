package com.me.ui.sample.widget.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.me.ui.sample.base.FullColorAdapter;
import com.me.ui.sample.library.util.ColorUtils;

/**
 * @author kylingo
 * @since 2019/09/30 09:44
 */
public class SnapPagerFragment extends BaseFragment {

    SnapHelper mSnapHelper;
    RecyclerView mRecyclerView;
    FullColorAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_snap_pager;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_snap_pager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FullColorAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(mRecyclerView);

        mAdapter.setData(ColorUtils.getData(20));
    }


}
