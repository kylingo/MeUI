package com.me.ui.sample.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kylingo on 18/4/8
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mItems;

    public BaseRecyclerViewAdapter() {
        mItems = new ArrayList<>();
    }

    public void setItems(List<T> items) {
        if (items != null) {
            mItems.clear();
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void addItems(List<T> items) {
        if (items != null) {
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
