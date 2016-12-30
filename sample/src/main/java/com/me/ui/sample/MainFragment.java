package com.me.ui.sample;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.bean.FragmentBean;
import com.me.ui.sample.refresh.RefreshFragment;

import java.util.List;

/**
 * Created by tangqi on 16-12-29.
 */

public class MainFragment extends BaseListFragment {

    @Override
    protected void addFragment(List<FragmentBean> fragmentBeans) {
        fragmentBeans.add(new FragmentBean("Refresh", RefreshFragment.class));
    }

}
