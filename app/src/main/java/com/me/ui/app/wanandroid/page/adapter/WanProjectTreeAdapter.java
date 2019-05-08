package com.me.ui.app.wanandroid.page.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseAdapter;
import com.me.ui.app.wanandroid.data.WanProjectTreeBean;

/**
 * @author kylingo
 * @since 2019/05/08 14:49
 */
public class WanProjectTreeAdapter extends BaseAdapter<WanProjectTreeBean> implements View.OnClickListener {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wan_project_tree, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        WanProjectTreeBean wanProjectTreeBean = getItem(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        if (wanProjectTreeBean != null) {
            holder.tvTitle.setText(wanProjectTreeBean.getName());

            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        WanProjectTreeBean wanProjectTreeBean = getItem(position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_project_tree_title);
        }
    }
}
