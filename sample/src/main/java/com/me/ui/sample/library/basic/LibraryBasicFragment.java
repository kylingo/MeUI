package com.me.ui.sample.library.basic;

import android.content.Intent;

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
        items.add(new FragmentBean("DataBinding", null));
        items.add(new FragmentBean("照相", TakePhotoFragment.class));
    }

    @Override
    protected void onClickItem(FragmentBean item) {
        switch (item.title) {
            case "DataBinding": {
                Intent intent = new Intent(getActivity(), DataBindingActivity.class);
                startActivity(intent);
                return;
            }
        }

        super.onClickItem(item);
    }
}
