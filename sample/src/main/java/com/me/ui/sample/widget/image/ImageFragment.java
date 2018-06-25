package com.me.ui.sample.widget.image;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.widget.image.filter.ImageFilterFragment;

import java.util.List;

/**
 * @author kylingo on 18/6/25
 */
public class ImageFragment extends SampleListFragment {
    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("图片滤镜", ImageFilterFragment.class));
    }
}
