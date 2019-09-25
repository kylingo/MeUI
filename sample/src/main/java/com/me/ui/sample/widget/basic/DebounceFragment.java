package com.me.ui.sample.widget.basic;

import android.view.View;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.me.ui.sample.library.log.MLog;

/**
 * @author kylingo
 * @since 2019/09/25 17:36
 */
public class DebounceFragment extends BaseFragment {

    private static final String TAG = "DebounceFragment";

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_debounce_click;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btn_debounce).setOnClickListener(new DebounceOnClickListener() {
            @Override
            public void doClick(View v) {
                MLog.d(TAG, "debounce click");
            }
        });
    }
}
