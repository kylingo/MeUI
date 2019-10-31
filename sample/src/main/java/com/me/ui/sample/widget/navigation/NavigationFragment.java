package com.me.ui.sample.widget.navigation;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;

import java.util.List;

/**
 * @author kylingo on 18/6/25
 */
public class NavigationFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("底部导航", BottomNavigationFragment.class));
        items.add(new FragmentBean("侧滑导航", DrawerNavigationFragment.class));
    }
}
