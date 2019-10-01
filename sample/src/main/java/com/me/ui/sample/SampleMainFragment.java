package com.me.ui.sample;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.algorithm.AlgorithmFragment;
import com.me.ui.sample.library.LibraryFragment;
import com.me.ui.sample.pattern.PatternFragment;
import com.me.ui.sample.thirdparty.ThirdPartyFragment;
import com.me.ui.sample.widget.WidgetFragment;

import java.util.List;

/**
 * @author tangqi on 16-12-29.
 */

public class SampleMainFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("组件", WidgetFragment.class));
        items.add(new FragmentBean("代码库", LibraryFragment.class));
        items.add(new FragmentBean("实验室", ThirdPartyFragment.class));
        items.add(new FragmentBean("设计模式", PatternFragment.class));
        items.add(new FragmentBean("算法艺术", AlgorithmFragment.class));
    }
}
