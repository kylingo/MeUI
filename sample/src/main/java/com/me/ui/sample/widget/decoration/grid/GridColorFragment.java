package com.me.ui.sample.widget.decoration.grid;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.ui.sample.widget.decoration.BaseListFragment;

/**
 * @author tangqi on 17-6-20.
 */
public class GridColorFragment extends BaseListFragment {

    private static final int SPAN_COUNT = 5;

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), SPAN_COUNT);
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new GridColorDecoration(getActivity());
    }
}
