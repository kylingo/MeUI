package com.me.ui.sample.widget.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.me.ui.util.ScreenUtils;
import com.me.ui.util.SizeUtils;

import java.util.Random;

/**
 * 飘屏动画
 *
 * @author kylingo on 18/6/29
 * @see #startAnim(Object)
 */
public class ExplodeViewManager {
    // 默认礼物个数
    private static final int COUNT_DEFAULT = 50;

    // 礼物上抛时间
    private static final int DURATION_ANIM_UP = 800;
    // 礼物下落时间
    private static final int DURATION_ANIM_DOWN = 3200;
    // 礼物上抛每个元素延时
    private static final int DELAY_ITEM_ANIM_UP = 10;
    // 礼物下落抛每个元素延时
    private static final int DELAY_ITEM_ANIM_DOWN = 30;

    private View mFromView;
    private ViewGroup mContainer;
    private int mScreenWidth;
    private int mGiftCount;
    private int mTopY;
    private int mBottomY;

    private int mImageSize;
    private int mDownDuration;

    public ExplodeViewManager(View fromView, ViewGroup container) {
        mFromView = fromView;
        mContainer = container;

        mGiftCount = COUNT_DEFAULT;
        mDownDuration = DURATION_ANIM_DOWN;
    }

    /**
     * 展示动画
     *
     * @param resource 图片资源bitmap或drawable
     */
    public void show(Object resource) {
        if (mFromView == null || mContainer == null) {
            return;
        }

        // 屏幕宽度
        mScreenWidth = ScreenUtils.getScreenWidth();
        // 动画最高点Y轴位置
        mTopY = -1 * SizeUtils.dp2px(100);
        // 动画最低点Y轴位置
        mBottomY = ScreenUtils.getScreenHeight() + SizeUtils.dp2px(100);
        // 图片大小
        mImageSize = SizeUtils.dp2px(42);

        // 加载图片
        startAnim(resource);
    }

    /**
     * 开始动画
     */
    private void startAnim(Object resource) {
        Drawable drawable = null;
        Bitmap bitmap = null;
        if (resource instanceof Drawable) {
            drawable = (Drawable) resource;
        } else if (resource instanceof Bitmap && !((Bitmap) resource).isRecycled()) {
            bitmap = (Bitmap) resource;
        } else {
            return;
        }

        for (int i = 0; i < mGiftCount; i++) {
            if (mContainer != null) {
                final ImageView imageView = new ImageView(mContainer.getContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(mImageSize, mImageSize));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setVisibility(View.INVISIBLE);
                if (drawable != null) {
                    imageView.setImageDrawable(drawable);
                } else {
                    imageView.setImageBitmap(bitmap);
                }
                mContainer.addView(imageView);
                initExplodeItem(imageView, i);
            }
        }
    }

    private void initExplodeItem(final ImageView imageView, final int index) {
        // 缩放大小
        float scale = new Random().nextInt(13) / 10f + 0.7f;
        // 旋转角度
        float rotation = new Random().nextInt(360);
        // 设置透明度
        float alpha = 1f;
        if (index % 2 == 0) {
            alpha = new Random().nextInt(7) / 10f + 0.3f;
        }

        // 设置礼物属性变换动画
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, alpha);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, scale);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, scale);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofFloat("rotation", rotation);
        ObjectAnimator giftAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, pvhAlpha,
                pvhX, pvhY, pvhRotation);
        giftAnimator.setDuration(1);
        giftAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                explodeUp(imageView, index);
            }
        });
        giftAnimator.start();
    }

    private void explodeUp(final ImageView imageView, final int index) {
        // 从元素的视图位置，开始向上喷
        int[] location = new int[2];
        if (mFromView != null) {
            mFromView.getLocationOnScreen(location);
        }
        float startX = location[0];
        float startY = location[1];

        // 元素向上喷的终点位置，在屏幕上方
        int endX = new Random().nextInt(mScreenWidth);
        int endY = mTopY;

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(DURATION_ANIM_UP);
        valueAnimator.setObjectValues(new PointF(startX, startY), new PointF(endX, endY));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF pointF = new PointF();
                // x方向匀速移动
                pointF.x = startValue.x + fraction * (endValue.x - startValue.x);
                // y方向抛物线加速
                pointF.y = startValue.y + 1.5f * fraction * fraction * (endValue.y - startValue.y);
                return pointF;
            }
        });

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                // 向上喷出时，才设置可见
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                // 向上抛完，再下落
                explodeDown(imageView, index);
            }
        });

        valueAnimator.setStartDelay(DELAY_ITEM_ANIM_UP * index);
        valueAnimator.start();
    }

    private void explodeDown(final ImageView imageView, int index) {
        int startX = new Random().nextInt(mScreenWidth);
        // 元素下落的起点位置，在屏幕上方，终点在屏幕下方
        int startY = mTopY;
        int endY = mBottomY;

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(mDownDuration);
        valueAnimator.setObjectValues(new PointF(startX, startY), new PointF(startX, endY));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF pointF = new PointF();
                pointF.x = startValue.x + fraction * (endValue.x - startValue.x);
                pointF.y = startValue.y + 3 * fraction * fraction * (endValue.y - startValue.y);
                return pointF;
            }
        });

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mContainer != null) {
                    mContainer.removeView(imageView);
                }
            }
        });

        valueAnimator.setStartDelay(DELAY_ITEM_ANIM_DOWN * index);
        valueAnimator.start();
    }
}
