package com.me.ui.sample;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.base.FragmentBean;
import com.me.ui.sample.refresh.RefreshFragment;

import java.util.List;

/**
 * @author tangqi on 16-12-29.
 */

public class MainFragment extends BaseListFragment {

    @Override
    protected void showFragment(List<FragmentBean> fragmentBeans) {
        fragmentBeans.add(new FragmentBean("Refresh", RefreshFragment.class));
    }

}
