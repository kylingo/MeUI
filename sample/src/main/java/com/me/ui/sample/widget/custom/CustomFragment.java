package com.me.ui.sample.widget.custom;

import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.library.sample.FragmentBean;

import java.util.List;

/**
 * @author tangqi on 17-3-6.
 */
public class CustomFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("Basic", CustomBasicFragment.class));
    }
}
