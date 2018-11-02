package com.me.ui.sample.widget.basic;

import android.content.Intent;

import com.me.ui.library.sample.SampleFragment;

import java.util.List;

/**
 * @author tangqi
 * @since 2018/11/02 15:02
 */
public class StatusBarFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("状态栏");
        items.add("背景图");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "状态栏": {
                Intent intent = new Intent(getActivity(), StatusBarColorActivity.class);
                startActivity(intent);
                break;
            }

            case "背景图": {
                Intent intent = new Intent(getActivity(), StatusBarImageActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
