package com.me.ui.sample.widget.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;

import java.util.Random;

/**
 * @author kylingo
 * @since 2019/09/24 16:27
 */
public class AnimSampleFragment extends BaseFragment {

    private static final int DURATION_ANIM = 3000;
    private LinearLayout mContainer;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_anim_sample;
    }

    @Override
    protected void initView(View view) {
        mContainer = view.findViewById(R.id.ll_anim);
        initFrameAnim(view);
        initTweenAnim(view);
        initPropertyAnim(view);
    }

    private void initFrameAnim(View view) {
        // xml
        ImageView ivFrame = view.findViewById(R.id.iv_anim_frame);
        AnimationDrawable frameDrawable = (AnimationDrawable) getResources()
                .getDrawable(R.drawable.anim_frame);
        ivFrame.setImageDrawable(frameDrawable);
        frameDrawable.start();

        // dynamic
        ImageView ivDynamicFrame = view.findViewById(R.id.iv_anim_dynamic_frame);
        AnimationDrawable frameDynamicDrawable = new AnimationDrawable();
        frameDynamicDrawable.addFrame(getResources()
                .getDrawable(R.drawable.anim_frame_03), 500);
        frameDynamicDrawable.addFrame(getResources()
                .getDrawable(R.drawable.anim_frame_04), 500);
        frameDynamicDrawable.addFrame(getResources()
                .getDrawable(R.drawable.anim_frame_01), 500);
        frameDynamicDrawable.addFrame(getResources()
                .getDrawable(R.drawable.anim_frame_02), 500);
        ivDynamicFrame.setImageDrawable(frameDynamicDrawable);
        frameDynamicDrawable.start();
    }

    /**
     * 补间动画
     * 注意：不会改变视图属性，动画执行完，视图会复原
     */
    private void initTweenAnim(View view) {
        // xml
        ImageView ivTween = view.findViewById(R.id.iv_anim_tween);
        ivTween.setBackgroundResource(R.drawable.ic_sample_launcher);
        final Animation tweenAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_tweened);
        ivTween.startAnimation(tweenAnimation);
        ivTween.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(tweenAnimation);
            }
        });

        // dynamic
        ImageView ivTweenDynamic = view.findViewById(R.id.iv_anim_dynamic_tween);
        ivTweenDynamic.setBackgroundResource(R.drawable.ic_sample_launcher);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(DURATION_ANIM);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 0.5f, 0.5f);
        scaleAnimation.setDuration(DURATION_ANIM);

        TranslateAnimation translateAnimation = new TranslateAnimation(-20, 20, -20, 20);
        translateAnimation.setDuration(DURATION_ANIM);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(DURATION_ANIM);

        final AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(getActivity(), android.R.anim.accelerate_decelerate_interpolator);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotateAnimation);

        ivTweenDynamic.startAnimation(animationSet);
        ivTweenDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationSet);
            }
        });
    }

    private void initPropertyAnim(View view) {
        initObjectAnimator(view);
        initValueAnimator(view);
        initAnimatorSet(view);
    }

    private void initObjectAnimator(View view) {
        ImageView ivObjectAnim = view.findViewById(R.id.iv_anim_object);
        ivObjectAnim.setBackgroundResource(R.drawable.ic_sample_launcher);
        ivObjectAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v, "rotationX", 0.0f, 360.0f)
                        .setDuration(500)
                        .start();
            }
        });

        ImageView ivObjectUpdateAnim = view.findViewById(R.id.iv_anim_object_update);
        ivObjectUpdateAnim.setBackgroundResource(R.drawable.ic_sample_launcher);
        ivObjectUpdateAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                boolean result = new Random().nextInt(10) % 2 == 0;
                if (result) {
                    // 推荐使用这种
                    PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
                    PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
                    PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
                    ObjectAnimator.ofPropertyValuesHolder(v, pvhAlpha, pvhX, pvhY)
                            .setDuration(500)
                            .start();
                } else {
                    @SuppressLint("ObjectAnimatorBinding") final ObjectAnimator anim = ObjectAnimator
                            .ofFloat(v, "kylingo", 0.0f, 1.0f)
                            .setDuration(500);
                    anim.start();
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            v.setAlpha(value);
                            v.setScaleX(value);
                            v.setScaleY(value);
                        }
                    });
                }
            }
        });
    }

    private void initValueAnimator(View view) {
        final ImageView ivValueAnim = view.findViewById(R.id.iv_anim_value);
        ivValueAnim.setBackgroundResource(R.drawable.ic_sample_launcher);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 400);
        animator.setTarget(ivValueAnim);
        animator.setDuration(500).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ivValueAnim.setTranslationX((Float) animation.getAnimatedValue());
            }
        });

        final ImageView ivValuePlusAnim = view.findViewById(R.id.iv_anim_value_plus);
        ivValuePlusAnim.setBackgroundResource(R.drawable.ic_sample_launcher);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(DURATION_ANIM);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF pointF = new PointF();
                pointF.x = 200 * fraction * 3;
                pointF.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return pointF;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                ivValuePlusAnim.setX(pointF.x);
                ivValuePlusAnim.setY(pointF.y);
            }
        });

        // Just set animation end listener
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    private void initAnimatorSet(View container) {
        final ImageView view = container.findViewById(R.id.iv_anim_set);
        view.setBackgroundResource(R.drawable.ic_sample_launcher);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float translation = 200f;

                ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
                ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", translation, 0f);
                ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", translation, 0f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0, 360);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setInterpolator(new LinearInterpolator());
                animatorSet.setDuration(500);
                animatorSet.play(scaleX)
                        .with(scaleY)
                        .with(translationX)
                        .with(translationY)
                        .with(alpha)
                        .with(rotation);

                animatorSet.start();
            }
        });
    }
}
