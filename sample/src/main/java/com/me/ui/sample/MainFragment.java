package com.me.ui.sample;

import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.base.FragmentBean;
import com.me.ui.sample.custom.CustomFragment;
import com.me.ui.sample.refresh.RefreshFragment;
import com.me.ui.sample.widget.WidgetFragment;

import java.util.List;

/**
 * @author tangqi on 16-12-29.
 */

public class MainFragment extends BaseListFragment {

    @Override
    protected void showFragment(List<FragmentBean> fragmentBeans) {
        fragmentBeans.add(new FragmentBean(CustomFragment.class));
        fragmentBeans.add(new FragmentBean(RefreshFragment.class));
        fragmentBeans.add(new FragmentBean(WidgetFragment.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        setActionBarTitle(R.string.app_name);
    }
}
