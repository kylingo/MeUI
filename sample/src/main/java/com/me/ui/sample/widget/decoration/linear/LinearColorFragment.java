package com.me.ui.sample.widget.decoration.linear;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.widget.decoration.LinearItemDecoration;

/**
 * @author tangqi on 17-6-20.
 */
public class LinearColorFragment extends BaseListFragment {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new LinearItemDecoration(getActivity());
    }
}
