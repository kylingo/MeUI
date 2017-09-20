package com.me.ui.sample.widget.decoration;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.widget.decoration.grid.GridColorFragment;
import com.me.ui.sample.widget.decoration.linear.LinearColorFragment;
import com.me.ui.sample.widget.decoration.staggered.StaggeredFragment;

import java.util.List;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    17/9/18 下午2:49
 */
public class DecorationFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("网格布局", GridColorFragment.class));
        items.add(new FragmentBean("线性布局", LinearColorFragment.class));
        items.add(new FragmentBean("瀑布布局", StaggeredFragment.class));
    }
}