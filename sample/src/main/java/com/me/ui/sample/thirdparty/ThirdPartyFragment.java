package com.me.ui.sample.thirdparty;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.thirdparty.arouter.ARouterFragment;

import java.util.List;

/**
 * @author kylingo on 18/10/10
 */
public class ThirdPartyFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean(ARouterFragment.class));
    }
}
