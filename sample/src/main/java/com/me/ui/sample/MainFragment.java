package com.me.ui.sample;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.library.LibraryFragment;
import com.me.ui.sample.widget.WidgetFragment;

import java.util.List;

/**
 * @author tangqi on 16-12-29.
 */

public class MainFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("组件", WidgetFragment.class));
        items.add(new FragmentBean("代码库", LibraryFragment.class));
    }
}
