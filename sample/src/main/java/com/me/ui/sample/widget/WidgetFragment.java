package com.me.ui.sample.widget;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.widget.anim.AnimFragment;
import com.me.ui.sample.widget.custom.CustomFragment;
import com.me.ui.sample.widget.decoration.DecorationFragment;
import com.me.ui.sample.widget.loading.LoadingFragment;
import com.me.ui.sample.widget.refresh.RefreshFragment;
import com.me.ui.sample.widget.web.WebIndexFragment;

import java.util.List;

/**
 * @author tangqi on 17-6-20.
 */
public class WidgetFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("测试", TestFragment.class));
        items.add(new FragmentBean("自定义", CustomFragment.class));
        items.add(new FragmentBean("下拉刷新", RefreshFragment.class));
        items.add(new FragmentBean("加载", LoadingFragment.class));
        items.add(new FragmentBean("分割线", DecorationFragment.class));
        items.add(new FragmentBean("网页加载", WebIndexFragment.class));
        items.add(new FragmentBean("动画", AnimFragment.class));
    }
}
