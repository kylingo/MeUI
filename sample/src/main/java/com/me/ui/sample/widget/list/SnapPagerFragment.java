package com.me.ui.sample.widget.list;

import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.base.FullColorAdapter;
import com.me.ui.sample.library.util.ColorUtils;

/**
 * @author kylingo
 * @since 2019/09/30 09:44
 */
public class SnapPagerFragment extends BaseListFragment {

    SnapHelper mSnapHelper;

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        super.initRecyclerView(recyclerView);

        mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        FullColorAdapter adapter = new FullColorAdapter();
        adapter.setData(ColorUtils.getData(20));
        return adapter;
    }
}
