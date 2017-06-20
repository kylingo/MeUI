package com.me.ui.sample.widget;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.widget.custom.CustomFragment;
import com.me.ui.sample.widget.refresh.RefreshFragment;

import java.util.List;

/**
 * @author tangqi on 17-6-20.
 */
public class WidgetFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("Custom", CustomFragment.class));
        items.add(new FragmentBean("Refresh", RefreshFragment.class));
    }
}
