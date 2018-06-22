package com.me.ui.sample.widget.loading;

import android.view.View;

import com.me.ui.sample.base.BaseFragment;
import com.me.ui.sample.R;
import com.me.ui.widget.loading.CircleLoadingView;
import com.me.ui.widget.loading.MeLoadingView;

;

/**
 * @author studiotang on 17/3/30
 */
public class LoadingFragment extends BaseFragment {

    private CircleLoadingView mCircleLoadingView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_widget_loading;
    }

    @Override
    protected void initView(View view) {
        MeLoadingView meLoadingView = (MeLoadingView) view.findViewById(R.id.me_loading_view);
        meLoadingView.appearAnim();

        mCircleLoadingView = (CircleLoadingView) view.findViewById(R.id.circle_loading_view);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCircleLoadingView.start();
            }
        }, 100);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCircleLoadingView.stop();
    }
}
