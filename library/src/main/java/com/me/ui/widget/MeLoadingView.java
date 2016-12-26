package com.me.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

public class MeLoadingView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final int ROTATE_DURATION = 900;
    private static final int DURATION = 300;
    private static final int DURATION2 = 100;
    private int mInitWidth ,mInitHeight;
    private float mBallRadius, mViewSize, mViewRadius;
    private int ballNum = 6;
    private ArrayList<BallsLoadingShapeHolder> mBalls = new ArrayList<BallsLoadingShapeHolder>(6);
    private ObjectAnimator mRotateAnim;
    private ArrayList<Integer> colorList = new ArrayList<Integer>(6);
    private AnimatorSet disappearAnim, appearAnim;

    private LeLoadingAnimListener animListener;
    private boolean isAnimRunning, isAppearAnimRunning, isDisAppearAnimRunning;
    private boolean isCancelAnim;
    private boolean mIsOnDraw;
    private boolean mDelayAppearAnim;

    public MeLoadingView(Context context) {
        super(context);
    }

    public MeLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] attrsArray = new int[]{
                android.R.attr.layout_width, // 0
                android.R.attr.layout_height // 1
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        int layout_width = ta.getDimensionPixelSize(0, ViewGroup.LayoutParams.MATCH_PARENT);
        int layout_height = ta.getDimensionPixelSize(1, ViewGroup.LayoutParams.MATCH_PARENT);
        ta.recycle();
        //init(layout_width, layout_height);
        mInitWidth = layout_width;
        mInitHeight = layout_height;
    }

    public ArrayList<Integer> getDefaultColorList() {
        ArrayList<Integer> colorList = new ArrayList<Integer>(6);
        colorList.add(Color.parseColor("#ed1e20"));
        colorList.add(Color.parseColor("#6c24c6"));
        colorList.add(Color.parseColor("#1ab1eb"));
        colorList.add(Color.parseColor("#8ad127"));
        colorList.add(Color.parseColor("#ffd800"));
        colorList.add(Color.parseColor("#ff8a00"));
        return colorList;
    }

    private void initBall() {
        float angleUnit = 360f / ballNum;
        float drawRadius = mViewRadius - mBallRadius;
        for (int i = 0; i < ballNum; i++) {
            PointF pointF = new PointF();
            pointF.set((float) (mViewSize / 2 + drawRadius * Math.sin(i * angleUnit * Math.PI / 180)),
                    (float) (mViewSize / 2 - drawRadius * Math.cos(i * angleUnit * Math.PI / 180)));
            mBalls.add(addBall(pointF.x, pointF.y, colorList.get(i)));
        }
    }

    private BallsLoadingShapeHolder addBall(float x, float y) {
        OvalShape circle = new OvalShape();
        circle.resize(mBallRadius, mBallRadius);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        BallsLoadingShapeHolder shapeHolder = new BallsLoadingShapeHolder(drawable);
        shapeHolder.setX(x);
        shapeHolder.setY(y);
        Paint paint = drawable.getPaint();
        paint.setColor(Color.RED);
        shapeHolder.setPaint(paint);
        shapeHolder.setAlpha(0f);
        return shapeHolder;
    }

    private BallsLoadingShapeHolder addBall(float x, float y, int color) {
        OvalShape circle = new OvalShape();
        circle.resize(mBallRadius, mBallRadius);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        BallsLoadingShapeHolder shapeHolder = new BallsLoadingShapeHolder(drawable);
        shapeHolder.setX(x);
        shapeHolder.setY(y);
        Paint paint = drawable.getPaint();
        paint.setColor(color);
        shapeHolder.setPaint(paint);
        shapeHolder.setAlpha(0f);
        return shapeHolder;
    }

    public boolean appearAnim() {
        int width = mInitWidth >0?mInitWidth :getWidth();
        int height = mInitHeight >0?mInitHeight :getHeight();
        return appearAnim(height,width,null);
    }

    public boolean appearAnim(int height , int width) {
        return appearAnim(height,width,null);
    }

    public boolean appearAnim(final Runnable disappearedCallBack) {
        int width = mInitWidth >0?mInitWidth :getWidth();
        int height = mInitHeight >0?mInitHeight :getHeight();
        return appearAnim(height,width,disappearedCallBack);
    }

    public boolean appearAnim(int height , int width,final Runnable disappearedCallBack) {
        if (isAnimRunning || isDisAppearAnimRunning) {
            return false;
        }
        isAnimRunning = true;
        int h = height;
        int w = width;
        int size = h >= w ? h : w;
        mViewSize = size;
        mBallRadius = size / (192 / 24);
        mViewRadius = size / (192 / 96);
        if (colorList.size() == 0) {
            colorList.addAll(getDefaultColorList());
        }
        if (appearAnim == null) {
            mBalls.clear();
            initBall();
            ObjectAnimator[] mAnimators = new ObjectAnimator[mBalls.size()];
            for (int i = 0, j = mBalls.size(); i < j; i++) {
                //0-normal
                mAnimators[i] = getZero2Normal(mBalls.get(i), i);
                mAnimators[i].setTarget(mBalls.get(i));
                mAnimators[i].addListener(new EmptyAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        ObjectAnimator objectAnimator = (ObjectAnimator) animation;
                        BallsLoadingShapeHolder holder = (BallsLoadingShapeHolder) objectAnimator.getTarget();
                        if (holder != null) {
                            holder.setAlpha(1f);
                        }
                    }
                });
            }
            mRotateAnim = getRotateAnim();
            appearAnim = new AnimatorSet();
            appearAnim.addListener(new EmptyAnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mRotateAnim.start();
                    isAppearAnimRunning = true;
                    if (animListener != null) {
                        animListener.onLoadStart();
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isAppearAnimRunning = false;
                    if (isCancelAnim) {
                        disappearAnim(disappearedCallBack);
                    }
                }
            });
            appearAnim.playTogether(mAnimators);
        }
        appearAnim.start();
        return true;
    }


    public void disappearAnim(final Runnable disappearedCallBack) {
        isCancelAnim = true;
        if (!isAnimRunning || isAppearAnimRunning) {
            return;
        }
        if (disappearAnim == null) {
            final ObjectAnimator[] mAnimators = new ObjectAnimator[mBalls.size()];
            for (int i = 0, j = mBalls.size(); i < j; i++) {
                //0-normal
                mAnimators[i] = getNormal2Zero(mBalls.get(i), i);
                mAnimators[i].setTarget(mBalls.get(i));
                mAnimators[i].addListener(new EmptyAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ObjectAnimator objectAnimator = (ObjectAnimator) animation;
                        BallsLoadingShapeHolder holder = (BallsLoadingShapeHolder) objectAnimator.getTarget();
                        if (holder != null) {
                            holder.setAlpha(0f);
                        }
                    }
                });
            }
            disappearAnim = new AnimatorSet();
            disappearAnim.addListener(new EmptyAnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    isDisAppearAnimRunning = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mRotateAnim.cancel();
                    isAnimRunning = false;
                    isDisAppearAnimRunning = false;
                    isCancelAnim = false;
                    if (animListener != null) {
                        animListener.onLoadFinished();
                    }
                    if (disappearedCallBack != null) {
                        disappearedCallBack.run();
                    }
                }

            });
            disappearAnim.playTogether(mAnimators);
        }
        disappearAnim.start();
    }


    /**
     * 0 - normal
     */
    private ObjectAnimator getZero2Normal(BallsLoadingShapeHolder ball, int orderId) {
        PropertyValuesHolder pvhW = PropertyValuesHolder.ofFloat("width",
                0, ball.getWidth());
        PropertyValuesHolder pvhH = PropertyValuesHolder.ofFloat("height",
                0, ball.getHeight());
        PropertyValuesHolder pvTX = PropertyValuesHolder.ofFloat("x",
                ball.getX() + mBallRadius / 2, ball.getX());
        PropertyValuesHolder pvTY = PropertyValuesHolder.ofFloat("y",
                ball.getY() + mBallRadius / 2, ball.getY());
        ObjectAnimator z2nAnim = ObjectAnimator.ofPropertyValuesHolder(
                ball, pvhW, pvhH, pvTX, pvTY).setDuration(DURATION / 2);
        z2nAnim.setStartDelay(75 * orderId);
        z2nAnim.setInterpolator(new AccelerateInterpolator());
        z2nAnim.addUpdateListener(this);
        return z2nAnim;
    }

    /**
     * normal - 0
     */
    private ObjectAnimator getNormal2Zero(BallsLoadingShapeHolder ball, int orderId) {
        PropertyValuesHolder pvhW = PropertyValuesHolder.ofFloat("width",
                ball.getWidth(), 0);
        PropertyValuesHolder pvhH = PropertyValuesHolder.ofFloat("height",
                ball.getHeight(), 0);
        PropertyValuesHolder pvTX = PropertyValuesHolder.ofFloat("x", ball.getX(),
                ball.getX() + mBallRadius / 2);
        PropertyValuesHolder pvTY = PropertyValuesHolder.ofFloat("y", ball.getY(),
                ball.getY() + mBallRadius / 2);
        ObjectAnimator z2nAnim = ObjectAnimator.ofPropertyValuesHolder(
                ball, pvhW, pvhH, pvTX, pvTY).setDuration(DURATION2 / 2);
        z2nAnim.setStartDelay(75 * orderId);
        z2nAnim.setInterpolator(new AccelerateInterpolator());
        z2nAnim.addUpdateListener(this);
        return z2nAnim;
    }

    private ObjectAnimator getRotateAnim() {
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotation",
                0, 360);
        ObjectAnimator rotateAnim = ObjectAnimator.ofPropertyValuesHolder(this, rotation).setDuration(ROTATE_DURATION);
        rotateAnim.setRepeatCount(-1); // -1:Infinite loop
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.addUpdateListener(this);
        return rotateAnim;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //if (drawBalls) {
        for (BallsLoadingShapeHolder ball : mBalls) {
            canvas.translate(ball.getX() - mBallRadius / 2, ball.getY() - mBallRadius / 2);
            ball.getShape().draw(canvas);
            canvas.translate(-ball.getX() + mBallRadius / 2, -ball.getY() + mBallRadius / 2);
        }
        //}
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    public int getBallNum() {
        return ballNum;
    }

    public void setBallNum(int ballNum, ArrayList<Integer> colorList) {

        if (colorList == null || colorList.size() < ballNum) {
            throw new IllegalArgumentException("colorList size < balls count");
        }
        this.colorList = colorList;
        this.ballNum = ballNum;
    }

    public ArrayList<Integer> getColorList() {
        return colorList;
    }

    public void setColorList(ArrayList<Integer> colorList) {
        this.colorList = colorList;
    }

    public LeLoadingAnimListener getAnimListener() {
        return animListener;
    }

    public void setAnimListener(LeLoadingAnimListener animListener) {
        this.animListener = animListener;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        invalidate();
    }

    public boolean isCancelAnim() {
        return isCancelAnim;
    }

    public void setCancelAnim(boolean isCancelAnim) {
        this.isCancelAnim = isCancelAnim;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mRotateAnim!=null){
            mRotateAnim.cancel();
        }
    }

    class EmptyAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }

    }

    /**
     * The AnimationProcessBar listener
     */
    public interface LeLoadingAnimListener {
        /**
         * when the animation start the method will be called
         */
        void onLoadStart();

        /**
         * when the animation finished the method will be called
         */
        void onLoadFinished();
    }
}
