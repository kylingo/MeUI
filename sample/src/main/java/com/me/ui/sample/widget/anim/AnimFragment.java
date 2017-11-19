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
        items.add(new FragmentBean("Lottie动画", LottieFragment.class));
    }
}
