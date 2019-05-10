package com.me.ui.app.wanandroid.page.fragment;

import android.view.View;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseFragment;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanLoginData;
import com.me.ui.app.wanandroid.data.WanModule;
import com.me.ui.app.wanandroid.page.view.WanTitleView;

import butterknife.BindView;
import butterknife.OnClick;

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
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_wan_register)
    public void onClickRegister() {
        WanNetEngine.getInstance().postUserRegister("kylingo", "123456", "123456")
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanLoginData>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {

                        }
                    }

                    @Override
                    public void onNext(WanModule<WanLoginData> wanLoginDataWanModule) {
                        if (wanLoginDataWanModule != null) {

                        }
                    }
                });

    }

    @OnClick(R.id.tv_wan_login_in)
    public void onClickLoginIn() {
        WanNetEngine.getInstance().postUserLogin("kylingo", "123456")
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanLoginData>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {

                        }
                    }

                    @Override
                    public void onNext(WanModule<WanLoginData> wanLoginDataWanModule) {
                        if (wanLoginDataWanModule != null) {

                        }
                    }
                });
    }

    @OnClick(R.id.tv_wan_login_out)
    public void onClickLoginOut() {
        WanNetEngine.getInstance().getUserLoginOut()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanLoginData>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {

                        }
                    }

                    @Override
                    public void onNext(WanModule<WanLoginData> wanLoginDataWanModule) {
                        if (wanLoginDataWanModule != null) {

                        }
                    }
                });
    }
}
