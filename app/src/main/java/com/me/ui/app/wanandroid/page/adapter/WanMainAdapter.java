package com.me.ui.app.wanandroid.page.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseAdapter;
import com.me.ui.app.wanandroid.data.WanArticleBean;
import com.me.ui.app.wanandroid.page.activity.WanWebActivity;
import com.me.ui.util.TimeUtils;

import java.util.List;

/**
 * @author tangqi
 * @since 2019/04/27 00:32
 */
public class WanMainAdapter extends BaseAdapter<WanArticleBean.DatasBean> implements View.OnClickListener {

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
            viewHolder.tvAuthor.setText(datasBean.getAuthor());
            String category = datasBean.getSuperChapterName();
            String chapterName = datasBean.getChapterName();
            if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(chapterName)) {
                category = category + " / " + chapterName;
            }
            viewHolder.tvCategory.setText(category);
            viewHolder.tvTime.setText(TimeUtils.millis2String(datasBean.getPublishTime()));

            String tag = null;
            List<WanArticleBean.DatasBean.Tag> tags = datasBean.getTags();
            if (tags != null && tags.size() > 0) {
                WanArticleBean.DatasBean.Tag tagBean = tags.get(0);
                tag = tagBean.getName();
            }
            if (TextUtils.isEmpty(tag)) {
                viewHolder.tvTag.setVisibility(View.GONE);
            } else {
                viewHolder.tvTag.setText(tag);
                viewHolder.tvTag.setVisibility(View.VISIBLE);
            }

            viewHolder.itemView.setTag(position);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        WanArticleBean.DatasBean datasBean = getItem(position);
        datasBean.getLink();

        WanWebActivity.launch(v.getContext(), datasBean.getTitle(), datasBean.getLink());
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvAuthor;
        public TextView tvCategory;
        public TextView tvTime;
        public TextView tvTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_wan_main_article);
            tvAuthor = itemView.findViewById(R.id.tv_wan_main_article_author);
            tvCategory = itemView.findViewById(R.id.tv_wan_main_article_category);
            tvTime = itemView.findViewById(R.id.tv_wan_main_article_time);
            tvTag = itemView.findViewById(R.id.tv_wan_main_article_tag);

            itemView.setOnClickListener(WanMainAdapter.this);
        }
    }
}
