package com.me.ui.sample.widget.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;

/**
 * 收缩，扩张动画
 * @author kylingo
 * @since 2019/11/20 10:41
 */
public class ShrinkFragment extends BaseFragment {

    // 动画默认展示时长
    private static final int TIME_SHOW_ANIM = 300;

    ImageView mImageView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_shrink;
    }

    @Override
    protected void initView(View view) {
        mImageView = view.findViewById(R.id.iv_shrink_anim);

        view.findViewById(R.id.btn_shrink_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAppearAnim();
//                showScaleY();
            }
        });

        view.findViewById(R.id.btn_shrink_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDisappearAnim();
//                dismissScaleY();
            }
        });
    }

    /*
     * 展示出现动画
     */
    public void showAppearAnim() {
        // 视图没加载时，无法获取高度，暂时估算为70dp
        int translationY = -(mImageView.getHeight());

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mImageView, "translationY", translationY, 0);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mImageView, "alpha", 0.0f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(TIME_SHOW_ANIM);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }

    /**
     * 展示消失动画
     */
    public void showDisappearAnim() {
        int translationY = -(mImageView.getHeight());

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mImageView, "translationY", 0, translationY);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mImageView, "alpha", 1.0f, 0.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(TIME_SHOW_ANIM);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    public void showScaleY() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mImageView, "scaleY", 0, 1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mImageView, "alpha", 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(TIME_SHOW_ANIM);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }

    public void dismissScaleY() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mImageView, "scaleY", 1f, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mImageView, "alpha", 1f, 0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(TIME_SHOW_ANIM);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }
}
