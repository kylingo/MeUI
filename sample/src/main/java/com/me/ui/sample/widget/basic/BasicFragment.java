package com.me.ui.sample.widget.basic;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.widget.TestFragment;

import java.util.List;

/**
 * @author kylingo on 18/6/21
 */
public class BasicFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("测试", TestFragment.class));
        items.add(new FragmentBean("文本样式", SpanFragment.class));
        items.add(new FragmentBean("主题切换", ThemeFragment.class));
        items.add(new FragmentBean("Clip测试", ClipChildrenFragment.class));
        items.add(new FragmentBean("全站消息", GlobalMessageFragment.class));
        items.add(new FragmentBean("约束布局", ConstraintFragment.class));
        items.add(new FragmentBean("协调布局", CoordinatorFragment.class));
        items.add(new FragmentBean("沉浸式", StatusBarFragment.class));
    }
}
