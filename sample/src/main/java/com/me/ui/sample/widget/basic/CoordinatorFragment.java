package com.me.ui.sample.widget.basic;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.ui.library.sample.AbstractSampleFragment;
import com.me.ui.sample.R;
import com.me.ui.sample.base.ColorAdapter;
import com.me.ui.sample.library.util.ColorUtils;
import com.me.ui.widget.decoration.LinearItemDecoration;

/**
 * @author tangqi
 * @since 2018/10/26 11:08
 */
public class CoordinatorFragment extends AbstractSampleFragment {

    protected RecyclerView mRecyclerView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_coordinator;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_coordinator);
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
        adapter.setData(ColorUtils.getData(50));
        return adapter;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new LinearItemDecoration(getActivity());
    }
}
