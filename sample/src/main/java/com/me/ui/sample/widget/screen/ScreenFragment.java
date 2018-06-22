package com.me.ui.sample.widget.screen;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;

import java.util.List;

/**
 * @author kylingo on 18/6/22
 */
public class ScreenFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("页面截屏", ScreenShotFragment.class));
        items.add(new FragmentBean("滚动截屏", ScrollShotFragment.class));
        items.add(new FragmentBean("网页截屏", WebShotFragment.class));
    }
}
