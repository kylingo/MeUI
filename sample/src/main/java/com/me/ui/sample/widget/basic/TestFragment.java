package com.me.ui.sample.widget.basic;

import android.content.Intent;
import android.widget.Toast;

import com.me.ui.library.sample.SampleFragment;

import java.util.List;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    18/3/20 11:52
 */
public class TestFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("Toast");
        items.add("输入法");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "Toast":
                // 部分手机的Toast有问题，显示"应用名称:toast"
                Toast.makeText(getContext(), "这是一个Toast", Toast.LENGTH_SHORT).show();
                break;

            case "输入法": {
                Intent intent = new Intent(getActivity(), InputMethodActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
