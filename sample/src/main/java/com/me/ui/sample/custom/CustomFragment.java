package com.me.ui.sample.custom;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;

import java.util.List;

/**
 * @author tangqi on 17-3-6.
 */
public class CustomFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean(BasicFragment.class));
    }
}
