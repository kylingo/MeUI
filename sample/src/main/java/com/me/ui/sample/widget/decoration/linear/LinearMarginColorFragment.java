package com.me.ui.sample.widget.decoration.linear;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.base.ColorAdapter;
import com.me.ui.sample.base.ColorItem;
import com.me.ui.sample.library.util.ColorUtils;
import com.me.ui.widget.decoration.LinearItemDecoration;

import java.util.List;

/**
 * 线性带
 * @author tangqi on 17-6-20.
 */
public class LinearMarginColorFragment extends BaseListFragment {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new LinearItemDecoration(getActivity());
    }

    @Override
    protected List<ColorItem> getData() {
        return ColorUtils.getData(3);
    }

    protected RecyclerView.Adapter getAdapter() {
        ColorAdapter adapter = new OverlayAdapter();
        adapter.setItemHeight(getItemHeight());
        adapter.setData(getData());
        return adapter;
    }

    public static class OverlayAdapter extends ColorAdapter {

        @NonNull
        @Override
        public ColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_margin, parent, false);
            return new ColorHolder(view, mItemHeight);
        }
    }
}
