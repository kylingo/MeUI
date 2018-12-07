package com.me.ui.sample.library.plugin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.library.util.CopyUtils;

import java.util.List;

/**
 * @author kylingo
 * @since 2018/12/07 15:38
 */
public class PluginFragment extends SampleListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CopyUtils.assetsCopy(getActivity(), "plugin", CopyUtils.getCachePath(getActivity()));
    }

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("自定义插件接口", CustomInterfaceFragment.class));
        items.add(new FragmentBean("自定义插件类加载", CustomClassFragment.class));
        items.add(new FragmentBean("自定义插件反射", CustomReflectFragment.class));
    }
}
