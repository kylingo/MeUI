package com.me.ui.app.wanandroid.page.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseAdapter;
import com.me.ui.app.wanandroid.data.WanArticleBean;

/**
 * @author tangqi
 * @since 2019/04/27 00:32
 */
public class WanMainAdapter extends BaseAdapter<WanArticleBean.DatasBean> {

    public WanMainAdapter() {
        super();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wan_main_article, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WanArticleBean.DatasBean datasBean = getItem(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (datasBean != null) {
            viewHolder.tvTitle.setText(datasBean.getTitle());
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_wan_main_article);
        }
    }
}
