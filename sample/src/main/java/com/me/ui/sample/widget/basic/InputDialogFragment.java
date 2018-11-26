package com.me.ui.sample.widget.basic;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseDialogFragment;
import com.me.ui.sample.library.util.InputMethodUtils;

/**
 * @author kylingo
 * @since 2018/11/26 11:02
 */
public class InputDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    public static final String TAG = "InputDialogFragment";
    private EditText mEditText;
    private TextView mTvSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_method, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mTvSend = view.findViewById(R.id.tv_input_send);
        mTvSend.setOnClickListener(this);

        mEditText = view.findViewById(R.id.et_input);
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                showInputMethod();
            }
        }, 100);
    }

    public void showInputMethod() {
        mEditText.setFocusable(true);
        mEditText.requestFocus();
        InputMethodUtils.showKeyboard(getActivity(), mEditText);
    }

    public void hideInputMethod() {
        InputMethodUtils.hideKeyboard(getActivity(), mEditText);
    }

    @Override
    protected int getWindowHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_input_send:
                dismiss();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        hideInputMethod();
    }
}
