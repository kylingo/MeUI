package com.me.ui.app.common.entrance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseActivity;
import com.me.ui.app.wanandroid.page.activity.WanMainActivity;

import butterknife.BindView;

/**
 * 欢迎页面
 *
 * @author tangqi
 * @date 2015/10/16 14:13
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initImage();
    }

    /**
     * 加载图片及动画
     */
    private void initImage() {
        mIvSplash.setImageResource(R.mipmap.ic_splash);
        mIvSplash.postDelayed(this::startActivity, 500);

//        // 逐渐变大
//        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                0.5f);
//        scaleAnim.setFillAfter(true);
//        scaleAnim.setDuration(2000);
//        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                startActivity();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//        mIvSplash.startAnimation(scaleAnim);
    }

    /**
     * 跳转到主页
     */
    private void startActivity() {
        Intent intent = new Intent(SplashActivity.this, WanMainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
