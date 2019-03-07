package com.me.ui.sample.thirdparty;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.thirdparty.arouter.ARouterFragment;
import com.me.ui.sample.thirdparty.aspect.AspectFragment;
import com.me.ui.sample.thirdparty.test.TestFragment;

import java.util.List;

/**
 * @author kylingo on 18/10/10
 */
public class ThirdPartyFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean(TestFragment.class));
        items.add(new FragmentBean(ARouterFragment.class));
        items.add(new FragmentBean(AspectFragment.class));
    }
}
