package com.me.ui.sample.widget.anim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;

/**
 * @author kylingo on 18/6/29
 */
public class ExplodeFragment extends BaseFragment {

    private ExplodeViewManager mExplodeViewManager;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_explode;
    }

    @Override
    protected void initView(View view) {
        RelativeLayout containerView = view.findViewById(R.id.rl_explode_container);
        Button btnStart = view.findViewById(R.id.btn_explode_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnim();
            }
        });
        mExplodeViewManager = new ExplodeViewManager(btnStart, containerView);
    }

    protected void showAnim() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        mExplodeViewManager.show(bitmap);
    }
}
