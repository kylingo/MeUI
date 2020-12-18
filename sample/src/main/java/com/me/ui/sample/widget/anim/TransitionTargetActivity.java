package com.me.ui.sample.widget.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseActivity;

/**
 * @author tangqi
 * @since 2020/12/18 15:46
 */
public class TransitionTargetActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_target);

        setTitle("Demo");
    }
}
