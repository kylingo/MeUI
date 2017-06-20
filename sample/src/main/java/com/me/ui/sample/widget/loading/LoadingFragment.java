package com.me.ui.sample.widget.loading;

import android.view.View;

import com.me.ui.sample.BaseFragment;
import com.me.ui.sample.R;
import com.me.ui.widget.loading.MeLoadingView;

;

/**
 * @author studiotang on 17/3/30
 */
public class LoadingFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_widget_loading;
    }

    @Override
    protected void initView(View view) {
        MeLoadingView meLoadingView = (MeLoadingView) view.findViewById(R.id.me_loading_view);
        meLoadingView.appearAnim();
    }
}
