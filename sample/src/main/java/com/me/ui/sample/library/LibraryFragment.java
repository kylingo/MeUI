package com.me.ui.sample.library;

import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleListFragment;
import com.me.ui.sample.library.basic.LibraryBasicFragment;
import com.me.ui.sample.library.download.DownloadFragment;
import com.me.ui.sample.library.log.LogFragment;
import com.me.ui.sample.library.security.EncryptFragment;

import java.util.List;

/**
 * @author tangqi on 17-6-20.
 */
public class LibraryFragment extends SampleListFragment {

    @Override
    protected void addItems(List<FragmentBean> items) {
        items.add(new FragmentBean("基础", LibraryBasicFragment.class));
        items.add(new FragmentBean("加密", EncryptFragment.class));
        items.add(new FragmentBean("下载", DownloadFragment.class));
        items.add(new FragmentBean("日志", LogFragment.class));
    }
}
