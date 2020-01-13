package com.me.ui.app.wanandroid.page.fragment;

import android.content.Intent;

import com.me.ui.app.wanandroid.page.activity.WanNavigationActivity;
import com.me.ui.app.wanandroid.page.activity.WanProjectTreeActivity;
import com.me.ui.library.sample.SampleFragment;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/05/08 12:51
 */
public class WanFindListFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("导航");
        items.add("项目");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "导航": {
                Intent intent = new Intent(getActivity(), WanNavigationActivity.class);
                startActivity(intent);
                break;
            }

            case "项目": {
                Intent intent = new Intent(getActivity(), WanProjectTreeActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
