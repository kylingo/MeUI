package com.me.ui.sample.widget.anim;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseActivity;

/**
 * @author tangqi
 * @since 2020/12/18 15:46
 */
public class TransitionActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        findViewById(R.id.iv_transition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTransition(v);
            }
        });
    }

    private void onClickTransition(View v) {
        Activity activity = TransitionActivity.this;
        Intent intent = new Intent(activity, TransitionTargetActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, v, "sharedImage");
            startActivity(intent, activityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
