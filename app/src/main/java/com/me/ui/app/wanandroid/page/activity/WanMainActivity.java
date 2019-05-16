package com.me.ui.app.wanandroid.page.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanBannerBean;
import com.me.ui.app.wanandroid.data.WanListModule;
import com.me.ui.app.wanandroid.data.WanWebsiteBean;
import com.me.ui.app.wanandroid.page.fragment.WanFindFragment;
import com.me.ui.app.wanandroid.page.fragment.WanMainFragment;
import com.me.ui.app.wanandroid.page.fragment.WanMeFragment;
import com.me.ui.app.wanandroid.page.fragment.WanTreeFragment;

/**
 * @author tangqi
 * @since 2019/04/22 23:51
 */
public class WanMainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private WanMainFragment mFirstFragment;
    private WanTreeFragment mSecondFragment;
    private WanFindFragment mThirdFragment;
    private WanMeFragment mFourthFragment;

    private String mFormerTag;
    private final static String FIRST_TAG = "FirstFragment";
    private final static String SECOND_TAG = "SecondFragment";
    private final static String THIRD_TAG = "ThirdFragment";
    private final static String FOURTH_TAG = "FourthFragment";

    private long exitTime;
    private final static long TIME_DIFF = 2 * 1000;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wan_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RadioGroup mGroup = findViewById(R.id.main_radio);
        mFirstFragment = new WanMainFragment();
        mSecondFragment = new WanTreeFragment();
        mThirdFragment = new WanFindFragment();
        mFourthFragment = new WanMeFragment();
        mFormerTag = FIRST_TAG;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_content, mFirstFragment, FIRST_TAG).commit();
        mGroup.setOnCheckedChangeListener(this);

//        apiTest();
    }

    /**
     * 首页导航切换
     */
    @Override
    public void onCheckedChanged(RadioGroup arg0, int checkedId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = fragmentManager.beginTransaction();
        Fragment preFragment = fragmentManager.findFragmentByTag(mFormerTag);
        if (preFragment != null) {
            mTransaction.hide(preFragment);
        }

        switch (checkedId) {
            // 博客
            case R.id.radiobutton_blogger:
                mFormerTag = FIRST_TAG;
                if (mFirstFragment.isAdded()) {
                    mTransaction.show(mFirstFragment).commit();
                } else {
                    mTransaction.add(R.id.main_content, mFirstFragment, mFormerTag).commit();
                }
                break;

            // 频道
            case R.id.radiobutton_channel:
                mFormerTag = SECOND_TAG;
                if (mSecondFragment.isAdded()) {
                    mTransaction.show(mSecondFragment).commit();
                } else {
                    mTransaction.add(R.id.main_content, mSecondFragment, mFormerTag).commit();
                }
                break;

            // 发现
            case R.id.radiobutton_find:
                mFormerTag = THIRD_TAG;
                if (mThirdFragment.isAdded()) {
                    mTransaction.show(mThirdFragment).commit();
                } else {
                    mTransaction.add(R.id.main_content, mThirdFragment, mFormerTag).commit();
                }
                break;

            // 个人
            case R.id.radiobutton_me:
                mFormerTag = FOURTH_TAG;
                if (mFourthFragment.isAdded()) {
                    mTransaction.show(mFourthFragment).commit();
                } else {
                    mTransaction.add(R.id.main_content, mFourthFragment, mFormerTag).commit();
                }
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//            if ((System.currentTimeMillis() - exitTime) > TIME_DIFF) {
//                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                finish();
//                System.exit(0);
//            }
//            return true;
//        }

        return super.onKeyDown(keyCode, event);
    }

    private void apiTest() {
        WanNetEngine.getInstance().getMainBanner()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanBannerBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(WanListModule<WanBannerBean> wanBannerBeanWanListModule) {
                        if (wanBannerBeanWanListModule != null) {

                        }
                    }
                });

        WanNetEngine.getInstance().getMainWebsite()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanWebsiteBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(WanListModule<WanWebsiteBean> wanWebsiteBeanWanListModule) {
                        if (wanWebsiteBeanWanListModule != null) {

                        }
                    }
                });
    }
}
