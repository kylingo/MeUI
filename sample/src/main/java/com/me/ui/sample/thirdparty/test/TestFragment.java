package com.me.ui.sample.thirdparty.test;

import android.content.Intent;
import android.net.Uri;
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
        items.add("Scheme");
        items.add("Profile");
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

            case "Scheme": {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("imgotv://minivideo?vid=1d10db2c9dc44756a59e00312ab9144d"));
                startActivity(intent);
                break;
            }

            case "Profile": {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("imgotv://noahprofile?uid=6c7b934382814507b45df4cb8c99988d"));
                startActivity(intent);
                break;
            }
        }
    }
}
