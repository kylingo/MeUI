package com.me.ui.sample.widget.basic;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.sample.support.util.ThemeUtils;

import java.util.List;

/**
 * @author kylingo on 18/6/22
 */
public class ThemeFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("Light");
        items.add("Dark");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "Light":
                ThemeUtils.changeTheme(true);
                ThemeUtils.reCreate(getActivity());
                break;

            case "Dark":
                ThemeUtils.changeTheme(false);
                ThemeUtils.reCreate(getActivity());
                break;
        }
    }
}
