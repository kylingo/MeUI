package com.me.ui.app.wanandroid.page.fragment;

import android.view.View;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseFragment;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanArticleBean;
import com.me.ui.app.wanandroid.data.WanModule;
import com.me.ui.util.ToastUtils;

import java.util.List;

/**
 * @author tangqi
 * @since 2019/04/26 23:43
 */
public class WanMainFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_wan_main;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        WanNetEngine.getInstance().getMainArticleList(0)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(WanModule<WanArticleBean> wanModule) {
                        if (wanModule != null) {
                            WanArticleBean wanArticleBean = wanModule.getData();
                            if (wanArticleBean != null) {
                                List<WanArticleBean.DatasBean> datasBeans = wanArticleBean.getDatas();
                                if (datasBeans != null) {
                                    ToastUtils.showShort("datasBeans:" + datasBeans.size());
                                }
                            }
                        }
                    }
                });
    }
}
