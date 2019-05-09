package com.me.ui.app.wanandroid.page.fragment;

import android.view.View;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseFragment;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.page.view.WanTitleView;

import butterknife.BindView;

/**
 * @author kylingo
 * @since 2019/05/09 13:28
 */
public class WanMeFragment extends BaseFragment {

    @BindView(R.id.tv_wan_me_title)
    WanTitleView mWanTitleView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_wan_me;
    }

    @Override
    protected void initView(View view) {
        mWanTitleView.setTitle(getString(R.string.wan_me));
        view.findViewById(R.id.tv_wan_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WanNetEngine.getInstance().postUserRegister("kylingo", "123456");
            }
        });

        view.findViewById(R.id.tv_wan_login_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WanNetEngine.getInstance().postUserLogin("kylingo", "123456");
            }
        });
    }

    @Override
    protected void initData() {

    }

//    @OnClick(R.id.tv_wan_register)
//    public void onClickRegister() {
//
//    }

//    @OnClick(R.id.tv_wan_login_in)
//    public void onClickLoginIn() {
//
//    }

//    @OnClick(R.id.tv_wan_login_out)
//    public void onClickLoginOut() {
//
//    }
}
