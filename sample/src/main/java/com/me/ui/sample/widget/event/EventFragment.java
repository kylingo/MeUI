package com.me.ui.sample.widget.event;

import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.base.FullColorAdapter;
import com.me.ui.sample.library.util.ColorUtils;

/**
 * @author kylingo
 * @since 2019/09/30 11:31
 */
public class EventFragment extends BaseListFragment {

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        super.initRecyclerView(recyclerView);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        FullColorAdapter adapter = new FullColorAdapter();
        adapter.setData(ColorUtils.getData(20));
        adapter.updateEventButton(true);
        return adapter;
    }
}
