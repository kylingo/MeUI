package com.me.ui.sample.widget.basic;

import android.content.Intent;

import com.me.ui.library.sample.SampleFragment;
import com.mgtv.color.ColorActivity;

import java.util.List;

/**
 * @author tangqi
 * @since 2020/11/11 15:41
 */
public class ResourceFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("color");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "color":
                Intent intent = new Intent(getContext(), ColorActivity.class);
                startActivity(intent);
                break;
        }
    }
}
