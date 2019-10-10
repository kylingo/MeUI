package com.me.ui.sample.algorithm.leetcode;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/10/10 17:40
 * LeetCode: https://leetcode.com/problemset/algorithms/
 * NowCoder: https://www.nowcoder.com/ta/coding-interviews
 */
public class LeetCodeFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("LeetCode01", LeetCode01Fragment.class));
    }
}
