package com.me.ui.sample.algorithm;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.algorithm.interview.InterviewFragment;
import com.me.ui.sample.algorithm.leetcode.LeetCodeFragment;
import com.me.ui.sample.algorithm.search.SearchFragment;
import com.me.ui.sample.algorithm.sort.SortFragment;
import com.me.ui.sample.algorithm.structure.DataStructureFragment;

import java.util.List;

/**
 * @author tangqi on 17-6-22.
 */
public class AlgorithmFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("数据结构", DataStructureFragment.class));
        items.add(new FragmentBean("排序", SortFragment.class));
        items.add(new FragmentBean("查找", SearchFragment.class));
        items.add(new FragmentBean("LeetCode", LeetCodeFragment.class));
        items.add(new FragmentBean("面试题", InterviewFragment.class));
    }
}
