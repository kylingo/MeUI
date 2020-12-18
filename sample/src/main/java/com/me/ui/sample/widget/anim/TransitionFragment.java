package com.me.ui.sample.widget.anim;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.util.ActivityUtils;

import java.util.List;

/**
 * @author tangqi
 * @since 2020/12/18 15:16
 */
public class TransitionFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("页面过场");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "页面过场":
                ActivityUtils.startActivity(getActivity(), TransitionActivity.class);
                break;
        }
    }
}
