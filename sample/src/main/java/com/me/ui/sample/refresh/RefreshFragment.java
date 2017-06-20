package com.me.ui.sample.refresh;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;

import java.util.List;

/**
 * @author tangqi on 16-12-30.
 */

public class RefreshFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean(PtrFragment.class));
        items.add(new FragmentBean(UltraFragment.class));
        items.add(new FragmentBean(UltraTextFragment.class));
    }
}
