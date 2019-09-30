package com.me.ui.sample.widget.list;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/09/30 09:43
 */
public class ListIndexFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean(SnapPagerFragment.class));
        items.add(new FragmentBean(LinearPagerFragment.class));
    }
}
