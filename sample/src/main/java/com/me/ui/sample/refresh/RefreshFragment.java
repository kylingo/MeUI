package com.me.ui.sample.refresh;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.base.FragmentBean;

import java.util.List;

/**
 * @author tangqi on 16-12-30.
 */

public class RefreshFragment extends BaseListFragment {

    @Override
    protected void showFragment(List<FragmentBean> fragmentBeans) {
        fragmentBeans.add(new FragmentBean("Ptr", RefreshPtrFragment.class));
        fragmentBeans.add(new FragmentBean("Ultra", RefreshUltraFragment.class));
        fragmentBeans.add(new FragmentBean("UltraText", RefreshUltraTextFragment.class));
    }
}
