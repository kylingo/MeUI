package com.me.ui.sample.widget.custom;

import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.library.sample.FragmentBean;

import java.util.List;

/**
 * @author tangqi on 17-3-6.
 */
public class CustomFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("简单视图", CustomBasicFragment.class));
        items.add(new FragmentBean("时钟", ClockFragment.class));
        items.add(new FragmentBean("九宫格", NineGridFragment.class));
        items.add(new FragmentBean("涂鸦板", GraffitiFragment.class));
        items.add(new FragmentBean("摇骰子", DiceFragment.class));
    }
}
