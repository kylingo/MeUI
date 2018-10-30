package com.me.ui.sample.widget.basic;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.ui.library.sample.AbstractSampleFragment;
import com.me.ui.sample.R;
import com.me.ui.sample.base.ColorAdapter;
import com.me.ui.sample.base.ColorItem;
import com.me.ui.widget.decoration.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        adapter.setData(getData());
        return adapter;
    }

    protected List<ColorItem> getData() {
        List<ColorItem> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ColorItem mainItem = new ColorItem();
            mainItem.index = i;
            mainItem.color = getRandColorCode();
            list.add(mainItem);
        }

        return list;
    }

    public static int getRandColorCode() {
        Random random = new Random();
        return 0xff000000 | random.nextInt(0x00ffffff);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new LinearItemDecoration(getActivity());
    }
}
