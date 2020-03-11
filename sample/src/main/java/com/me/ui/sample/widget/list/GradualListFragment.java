package com.me.ui.sample.widget.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangqi
 * @since 2020/03/10 20:22
 */
public class GradualListFragment extends BaseListFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_gradual_list;
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.rv_gradual;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        LinearLayout linearLayout = view.findViewById(R.id.ll_gradual);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        GradualListAdapter adapter = new GradualListAdapter();
        adapter.setItems(getItems());
        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    }

    List<String> getItems() {
        List<String> items = new ArrayList<>();
        items.add("精选");
        items.add("综艺");
        items.add("电视剧");
        items.add("电影");
        items.add("少儿");
        items.add("动漫");
        items.add("新闻");
        items.add("直播");
        items.add("娱乐");
        items.add("教育");
        items.add("文学");
        items.add("记录片");
        return items;
    }
}
