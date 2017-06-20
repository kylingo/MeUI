package com.me.ui.sample.widget.refresh;

import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.library.sample.FragmentBean;

import java.util.List;

/**
 * @author tangqi on 16-12-30.
 */

public class RefreshFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("Ptr", RefreshPtrFragment.class));
        items.add(new FragmentBean("Ultra", RefreshUltraFragment.class));
        items.add(new FragmentBean("UltraText", RefreshUltraTextFragment.class));
    }
}
