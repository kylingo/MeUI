package com.me.ui.app.wanandroid.page.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseFragment;
import com.me.ui.app.wanandroid.page.view.WanTitleView;

import butterknife.BindView;

/**
 * @author kylingo
 * @since 2019/05/08 12:43
 */
public class WanFindFragment extends BaseFragment {

    @BindView(R.id.view_wan_find_title)
    WanTitleView mWanTitleView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_wan_find;
    }

    @Override
    protected void initView(View view) {
        mWanTitleView.setTitle(getString(R.string.wan_find));

        if (getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_wan_find, new WanFindListFragment()).commitAllowingStateLoss();
            }
        }

    }

    @Override
    protected void initData() {

    }
}
