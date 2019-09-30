package com.me.ui.sample.algorithm.structure;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.algorithm.structure.link.LinkFragment;
import com.me.ui.sample.algorithm.structure.tree.TreeFragment;

import java.util.List;

/**
 * @author tangqi on 17-7-14.
 */
public class DataStructureFragment extends SampleListFragment {
    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("链表", LinkFragment.class));
        items.add(new FragmentBean("二叉树", TreeFragment.class));
    }
}
