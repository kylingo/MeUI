package com.me.ui.sample.widget.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseActivity;

/**
 * @author kylingo
 * @since 2018/11/22 10:01
 */
public class InputMethodActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvInput;
    private LinearLayout mLlInput;
    private EditText mEtInput;
    private TextView mTvSend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_method);
        initView();
    }

    private void initView() {
        mTvInput = findViewById(R.id.tv_input);
        mLlInput = findViewById(R.id.ll_input);
        mEtInput = findViewById(R.id.et_input);
        mTvSend = findViewById(R.id.tv_input_send);

        mTvInput.setOnClickListener(this);
        mTvSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_input:
                showSendView();
                break;

            case R.id.tv_input_send:
                hideSendView();
                break;
        }
    }

    private void showSendView() {
        mTvInput.setVisibility(View.GONE);
        mLlInput.setVisibility(View.VISIBLE);
        mEtInput.setFocusable(true);
        mEtInput.requestFocus();

        showKeyboard();
    }

    private void hideSendView() {
        mTvInput.setVisibility(View.VISIBLE);
        mLlInput.setVisibility(View.GONE);
        hideKeyboard();
    }

    public void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(mEtInput, 0);
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(mEtInput.getWindowToken(), 0);
        }
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
