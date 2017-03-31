package com.me.ui.sample.widget;

import android.view.View;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.me.ui.widget.CircleLoadingView;

/**
 * @author studiotang on 17/3/30
 */
public class LoadingFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_widget_loading;
    }

    @Override
    protected void initView(View view) {
        CircleLoadingView meLoadingView = (CircleLoadingView) view.findViewById(R.id.me_loading_view);
        meLoadingView.appearAnim();
    }
}
