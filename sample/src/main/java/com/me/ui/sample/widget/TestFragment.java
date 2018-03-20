package com.me.ui.sample.widget;

import android.view.View;
import android.widget.Toast;

import com.me.ui.sample.BaseFragment;
import com.me.ui.sample.R;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    18/3/20 11:52
 */
public class TestFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btn_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 部分手机的Toast有问题，显示"应用名称:toast"
                Toast.makeText(getContext(), "这是一个Toast", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
