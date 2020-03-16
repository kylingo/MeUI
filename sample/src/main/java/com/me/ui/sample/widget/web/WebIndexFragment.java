package com.me.ui.sample.widget.web;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;

import java.util.List;

/**
 * @author studiotang on 17/9/19
 */
public class WebIndexFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("在线页面", WebFragment.class));
        items.add(new FragmentBean("本地页面", WebLocalFragment.class));
        items.add(new FragmentBean("Markdown", WebMdFragment.class));
        items.add(new FragmentBean("浏览器", WebBrowserFragment.class));
    }
}
