package com.me.ui.sample.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.ui.library.sample.AbstractSampleFragment;
import com.me.ui.sample.R;
import com.me.ui.sample.library.util.ColorUtils;

import java.util.List;

/**
 * @author tangqi on 17-6-20.
 */
public abstract class BaseListFragment extends AbstractSampleFragment {

    protected RecyclerView mRecyclerView;

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract RecyclerView.ItemDecoration getItemDecoration();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_common;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_common);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }

        RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
        if (itemDecoration != null) {
            mRecyclerView.addItemDecoration(itemDecoration);
        }

        initRecyclerView(mRecyclerView);
        RecyclerView.Adapter adapter = getAdapter();
        mRecyclerView.setAdapter(adapter);

    }

    protected void initRecyclerView(RecyclerView recyclerView) {

    }

    protected RecyclerView.Adapter getAdapter() {
        ColorAdapter adapter = new ColorAdapter();
        adapter.setData(getData());
        return adapter;
    }

    protected List<ColorItem> getData() {
        return ColorUtils.getData(50);
    }
}
