package com.me.ui.sample.widget;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.base.FragmentBean;

import java.util.List;

/**
 * @author studiotang on 17/3/30
 */
public class WidgetFragment extends BaseListFragment{
    @Override
    protected void showFragment(List<FragmentBean> fragmentBeans) {
        fragmentBeans.add(new FragmentBean(LoadingFragment.class));
    }
}
