package com.me.ui.sample.widget.basic;

import android.view.View;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;

/**
 * 跑马灯
 * @author kylingo
 * @since 2019/12/09 09:16
 */
public class MarqueeFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_marquee;
    }

    @Override
    protected void initView(View view) {
        View tvMarquee = view.findViewById(R.id.tvMarquee);
        tvMarquee.requestFocus();
    }
}
