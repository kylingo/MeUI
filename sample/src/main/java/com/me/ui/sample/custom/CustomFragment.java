package com.me.ui.sample.custom;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.base.FragmentBean;

import java.util.List;

/**
 * @author tangqi on 17-3-6.
 */
public class CustomFragment extends BaseListFragment {

    @Override
    protected void showFragment(List<FragmentBean> fragmentBeans) {
        fragmentBeans.add(new FragmentBean("Base", CustomBaseFragment.class));
    }
}
