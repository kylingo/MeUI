package com.me.ui.sample.thirdparty;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.thirdparty.alert.FloatingFragment;
import com.me.ui.sample.thirdparty.anr.HandlerFragment;
import com.me.ui.sample.thirdparty.arouter.ARouterFragment;
import com.me.ui.sample.thirdparty.aspect.AspectFragment;
import com.me.ui.sample.thirdparty.audio.AudioFragment;
import com.me.ui.sample.thirdparty.test.TestFragment;

import java.util.List;

/**
 * @author kylingo on 18/10/10
 */
public class ThirdPartyFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("测试", TestFragment.class));
        items.add(new FragmentBean("路由", ARouterFragment.class));
        items.add(new FragmentBean("AOP", AspectFragment.class));
        items.add(new FragmentBean("悬浮窗", FloatingFragment.class));
        items.add(new FragmentBean("音频", AudioFragment.class));
        items.add(new FragmentBean("ANR", HandlerFragment.class));
    }
}
