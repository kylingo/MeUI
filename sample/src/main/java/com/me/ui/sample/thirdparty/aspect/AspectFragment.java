package com.me.ui.sample.thirdparty.aspect;

import android.view.View;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.me.ui.util.LogUtils;

/**
 * @author kylingo
 * @since 2019/03/07 16:08
 */
public class AspectFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_base;
    }

    @Override
    protected void initView(View view) {
        LogUtils.i(TraceAspect.TAG, "AspectFragment testAspect");
        testAspect();

        LogUtils.i(TraceAspect.TAG, "AspectFragment testAspect1");
        testAspect1();

        LogUtils.i(TraceAspect.TAG, "AspectFragment testTryCatch");
        testTryCatch();
    }

    private void testAspect() {
        LogUtils.i(TraceAspect.TAG, "testAspect invoke");
    }

    private void testAspect1() {
        LogUtils.i(TraceAspect.TAG, "testAspect1 invoke");
    }

    @TryCatch
    private void testTryCatch() {
        String a = null;
        int size = a.length();
    }
}
