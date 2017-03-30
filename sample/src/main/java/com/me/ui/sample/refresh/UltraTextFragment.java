package com.me.ui.sample.refresh;

import android.view.View;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author tangqi on 16-12-30.
 *         Ultra: https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh
 */
public class UltraTextFragment extends BaseFragment {

    private PtrFrameLayout mPtrFrameLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_ultra_text;
    }

    @Override
    protected void initView(View rootView) {
        mPtrFrameLayout = (PtrFrameLayout) rootView.findViewById(R.id.ptr_frame_ultra);

        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getContext());
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrameLayout.refreshComplete();
                    }
                }, 2000);
            }
        });
    }
}
