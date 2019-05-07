package com.me.ui.app.wanandroid.page.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseAdapter;
import com.me.ui.app.wanandroid.data.WanTreeBean;
import com.me.ui.app.wanandroid.page.activity.WanTreeCategoryActivity;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/05/07 13:35
 */
public class WanTreeAdapter extends BaseAdapter<WanTreeBean> implements View.OnClickListener {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wan_tree, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WanTreeBean wanTreeBean = getItem(position);
        WanTreeAdapter.ViewHolder viewHolder = (ViewHolder) holder;

        if (wanTreeBean != null) {
            viewHolder.tvCategory.setText(wanTreeBean.getName());

            StringBuilder sb = new StringBuilder();
            List<WanTreeBean> childList = wanTreeBean.getChildren();
            if (childList != null) {
                for (WanTreeBean childBean : childList) {
                    sb.append(childBean.getName()).append("   ");
                }
            }
            viewHolder.tvChild.setText(sb.toString());
            viewHolder.itemView.setTag(position);
        }

    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        WanTreeBean wanTreeBean = getItem(position);
        WanTreeCategoryActivity.launch(v.getContext(), wanTreeBean);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCategory;
        public TextView tvChild;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_wan_tree_category);
            tvChild = itemView.findViewById(R.id.tv_wan_tree_child);

            itemView.setOnClickListener(WanTreeAdapter.this);
        }
    }
}
