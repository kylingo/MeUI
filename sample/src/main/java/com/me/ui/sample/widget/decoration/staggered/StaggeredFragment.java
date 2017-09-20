package com.me.ui.sample.widget.decoration.staggered;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.base.ColorAdapter;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    17/9/20 下午10:01
 */
public class StaggeredFragment extends BaseListFragment {

    private static final int SPAN_COUNT = 2;

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new StaggeredDecoration(10);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        ColorAdapter adapter = new StaggeredAdapter();
        adapter.addData(getData());
        return adapter;
    }
}