package com.me.ui.widget.custom.dice;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.me.ui.R;

import java.util.Random;

/**
 * 摇骰子
 * Author:  kevin.tang
 * Date:    17/9/19 上午10:50
 */
public class DiceView extends ImageView implements IDiceOperation {

    private static final String TAG = "DiceView";
    private static final int DEFAULT_DISMISS_TIME = 5000;
    private static final int DEFAULT_VALUE = 0;
    private static final long DEFAULT_DURATION = 2000;
    private int mValue = DEFAULT_VALUE;
    private long mDuration = DEFAULT_DURATION;

    private AnimationDrawable mAnimationDrawable;
    private UpdateRunnable mUpdateRunnable;
    private DismissRunnable mDismissRunnable;
    private DiceStateChangeListener mListener;
    /**
     * 动画是否在执行（部分机型上mAnimationDrawable.isRunning()无效，所以这里自己定义一个变量）
     **/
    private boolean isDiceAnimRunning = false;

    public DiceView(Context context) {
        this(context, null);
    }

    public DiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createRandomValue();
        initAnimation();
        initImageView();
    }

    protected void initAnimation() {
        mAnimationDrawable = getAnimationDrawable();
        mAnimationDrawable.setOneShot(false);
        mUpdateRunnable = new UpdateRunnable();
        mDismissRunnable = new DismissRunnable();
    }

    @SuppressWarnings("deprecation")
    protected AnimationDrawable getAnimationDrawable() {
        return (AnimationDrawable) getResources().getDrawable(R.drawable.anim_dice);
    }

    protected void initImageView() {
        if (mAnimationDrawable != null) {
            setImageDrawable(mAnimationDrawable);
        }
    }

    @Override
    public void start() {
        if (isDiceAnimRunning) {
            return;
        }

        initImageView();
        removeCallbacks(mDismissRunnable);
        isDiceAnimRunning = true;
        if (mAnimationDrawable != null) {
            mAnimationDrawable.start();
        }
        postDelayed(mUpdateRunnable, mDuration);
    }

    @Override
    public void start(int value) {
        if (isValueValid(value)) {
            mValue = value;
            start();
        }
    }

    @Override
    public void stop() {
        if (!isDiceAnimRunning) {
            return;
        }

        removeCallbacks(mUpdateRunnable);
        removeCallbacks(mDismissRunnable);
        if (mAnimationDrawable != null) {
            mAnimationDrawable.stop();
        }
        isDiceAnimRunning = false;
        updateDice();
    }

    @Override
    public void delayDismiss() {
        if (isDiceAnimRunning) {
            return;
        }

        postDelayed(mDismissRunnable, DEFAULT_DISMISS_TIME);
    }

    @Override
    public void reset() {
        createRandomValue();
    }

    @Override
    public void setDuration(long duration) {
        mDuration = duration;
    }

    @Override
    public int getValue() {
        return mValue;
    }

    @Override
    public void setStateChangeListener(DiceView.DiceStateChangeListener listener) {
        mListener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private class UpdateRunnable implements Runnable {

        @Override
        public void run() {
            stop();
        }
    }

    @SuppressWarnings("deprecation")
    private void updateDice() {
        if (isValueValid(mValue)) {
            setImageDrawable(getResources().getDrawable(
                    getResources().getIdentifier("dice_" + mValue, "drawable",
                            getContext().getPackageName())));
        }

        if (mListener != null) {
            mListener.onDiceAnimFinish(mValue);
        }
    }

    private class DismissRunnable implements Runnable {

        @Override
        public void run() {
            setVisibility(View.GONE);
            if (mListener != null) {
                mListener.onDiceHide();
            }
        }
    }

    /**
     * 生成一个伪随机数
     */
    private void createRandomValue() {
        mValue = new Random().nextInt(6) + 1;
    }

    /**
     * 判断骰子点数有效性
     */
    private boolean isValueValid(int value) {
        return value >= 1 && value <= 6;
    }

    public interface DiceStateChangeListener {
        /**
         * 骰子动画结束
         **/
        void onDiceAnimFinish(int value);

        /**
         * 骰子隐藏
         **/
        void onDiceHide();
    }
}