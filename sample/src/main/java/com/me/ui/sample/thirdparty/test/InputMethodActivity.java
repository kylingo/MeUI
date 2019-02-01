package com.me.ui.sample.thirdparty.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseActivity;
import com.me.ui.sample.library.util.FragmentUtils;

/**
 * @author kylingo
 * @since 2018/11/22 10:01
 */
public class InputMethodActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_method);
        initView();
    }

    private void initView() {
        TextView tvInput = findViewById(R.id.tv_input);
        tvInput.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_input:
                showSendView();
                break;
        }
    }

    private void showSendView() {
        final InputDialogFragment inputDialogFragment = new InputDialogFragment();
        FragmentUtils.show(this, inputDialogFragment, InputDialogFragment.TAG);
    }

    /**
     * adjustPan 会把整个布局顶上去
     * adjustResize 调整屏幕大小，给软键盘留出空间
     * adjustNothing 页面不会做任何调整
     * adjustUnspecified 系统会根据情况选择上述的一种模式，能滚动他们的内容
     */
    public void setWindowSoftInputMode(int mode) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
