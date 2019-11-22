package com.me.ui.sample.widget.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
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

    ImageView mIvShrink;
    ImageView mIvScale;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_shrink;
    }

    @Override
    protected void initView(View view) {
        mIvShrink = view.findViewById(R.id.iv_shrink_anim);
        mIvScale = view.findViewById(R.id.iv_scale_anim);

        view.findViewById(R.id.btn_shrink_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAppearAnim();
            }
        });

        view.findViewById(R.id.btn_shrink_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDisappearAnim();
            }
        });

        view.findViewById(R.id.btn_scale_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showScaleY();
            }
        });

        view.findViewById(R.id.btn_scale_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissScaleY();
            }
        });
    }

    /*
     * 展示出现动画
     */
    public void showAppearAnim() {
        // 视图没加载时，无法获取高度，暂时估算为70dp
        View targetView = mIvShrink;
        int translationY = -(targetView.getHeight());

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(targetView, "translationY", translationY, 0);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(targetView, "alpha", 0.0f, 1.0f);
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
        View targetView = mIvShrink;
        int translationY = -(targetView.getHeight());

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(targetView, "translationY", 0, translationY);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(targetView, "alpha", 1.0f, 0.0f);
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
        mIvScale.setVisibility(View.VISIBLE);
        View targetView = mIvScale;
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1f, 0f, 1f, 0f, 0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);

        final AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(getActivity(), android.R.anim.linear_interpolator);
        animationSet.setDuration(TIME_SHOW_ANIM);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        targetView.startAnimation(animationSet);
    }

    public void dismissScaleY() {
        View targetView = mIvScale;
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1f, 1f, 0f, 1f, 1f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvScale.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        final AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(getActivity(), android.R.anim.linear_interpolator);
        animationSet.setDuration(TIME_SHOW_ANIM);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        targetView.startAnimation(animationSet);
    }
}
