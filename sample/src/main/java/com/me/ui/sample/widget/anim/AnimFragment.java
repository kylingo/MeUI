package com.me.ui.sample.widget.anim;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;

import java.util.List;

/**
 * @author kylingo on 17/11/19
 */
public class AnimFragment extends SampleListFragment {
    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("基础动画", AnimSampleFragment.class));
        items.add(new FragmentBean("Lottie动画", LottieFragment.class));
        items.add(new FragmentBean("飘屏动画", ExplodeFragment.class));
    }
}
