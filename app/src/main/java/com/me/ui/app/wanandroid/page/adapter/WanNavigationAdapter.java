package com.me.ui.app.wanandroid.page.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseAdapter;
import com.me.ui.app.wanandroid.data.WanNavigationBean;
import com.me.ui.app.wanandroid.page.activity.WanWebActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/05/08 13:03
 */
public class WanNavigationAdapter extends BaseAdapter<WanNavigationBean> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wan_navigation, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        WanNavigationBean wanNavigationBean = getItem(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        if (wanNavigationBean != null) {
            holder.tvTitle.setText(wanNavigationBean.getName());
            List<WanNavigationBean.ArticlesBean> articlesBeans = wanNavigationBean.getArticles();

            holder.tagAdapter = new NavigationTagAdapter(articlesBeans);
            holder.tagFlowLayout.setAdapter(holder.tagAdapter);
            holder.tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    TagFlowLayout tagFlowLayout = (TagFlowLayout) parent;
                    WanNavigationBean.ArticlesBean bean = (WanNavigationBean.ArticlesBean) tagFlowLayout.getAdapter()
                            .getItem(position);
                    WanWebActivity.launch(view.getContext(), bean.getTitle(), bean.getLink());
                    return true;
                }
            });
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TagFlowLayout tagFlowLayout;
        public NavigationTagAdapter tagAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_wan_navigation_title);
            tagFlowLayout = itemView.findViewById(R.id.tag_navigation_flow);
        }
    }

    private class NavigationTagAdapter extends TagAdapter<WanNavigationBean.ArticlesBean> {

        public NavigationTagAdapter(List<WanNavigationBean.ArticlesBean> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, WanNavigationBean.ArticlesBean bean) {
            TextView tvTag = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wan_navigation_tag,
                    parent, false);
            tvTag.setText(bean.getTitle());
            return tvTag;
        }
    }
}
