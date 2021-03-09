package com.me.ui.sample.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ui.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangqi on 17-6-19.
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorHolder> {

    private List<ColorItem> mItems;
    protected int mItemHeight;

    public ColorAdapter() {
        init();
    }

    public void setItemHeight(int itemHeight) {
        // itemHeight传0，为默认高度
        mItemHeight = itemHeight;
    }

    private void init() {
        mItems = new ArrayList<>();
    }

    public void setData(List<ColorItem> items) {
        if (items != null) {
            mItems.clear();
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void addData(List<ColorItem> items) {
        if (items != null) {
            mItems.addAll(items);
        }
    }

    private ColorItem getItem(int position) {
        return mItems.get(position);
    }

    @NonNull
    @Override
    public ColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new ColorHolder(view, mItemHeight);
    }

    @Override
    public void onBindViewHolder(ColorHolder holder, int position) {
        ColorItem mainItem = getItem(position);
        holder.tvMain.setText(String.valueOf(mainItem.index));
        holder.tvMain.setBackgroundColor(mainItem.color);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ColorHolder extends RecyclerView.ViewHolder {

        public TextView tvMain;

        public ColorHolder(View itemView, int itemHeight) {
            super(itemView);
            tvMain = itemView.findViewById(R.id.tv_grid);
            if (itemHeight > 0) {
                tvMain.getLayoutParams().height = itemHeight;
            }
        }
    }
}
