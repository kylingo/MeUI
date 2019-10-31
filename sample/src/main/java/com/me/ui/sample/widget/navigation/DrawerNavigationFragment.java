package com.me.ui.sample.widget.navigation;

import com.me.ui.library.sample.SampleFragment;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/10/24 20:03
 */
public class DrawerNavigationFragment extends SampleFragment<String> {
    @Override
    protected void addItems(List<String> items) {
        items.add("系统侧滑");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "系统侧滑":
//                Intent intent = new Intent(getContext(), DrawerActivity.class);
//                startActivity(intent);
                break;
        }
    }
}
