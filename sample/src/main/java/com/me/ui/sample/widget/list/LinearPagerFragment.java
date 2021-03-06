package com.me.ui.sample.widget.list;

import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.util.SizeUtils;

/**
 * @author kylingo
 * @since 2019/09/30 10:14
 */
public class LinearPagerFragment extends BaseListFragment {

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
        super.initRecyclerView(recyclerView);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected int getItemHeight() {
        return SizeUtils.dp2px(300);
    }
}
