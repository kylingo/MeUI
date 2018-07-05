package com.me.ui.sample.library.basic;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;

import java.util.List;

/**
 * @author kylingo on 18/7/6
 */
public class LibraryBasicFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("注解", AnnotationFragment.class));
    }
}
