package com.me.ui.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.me.ui.R;

import java.util.Calendar;

/**
 * @author tangqi on 17-7-20.
 */
public class ClockView extends View {

    protected static final int DELAY_UPDATE = 1000;
    protected static final int DEFAULT_COLOR = Color.BLACK;

    protected Paint mPaint;
    protected Point mCenterPoint;
    protected Runnable mRunnable;

    protected int mRadius;
    protected int mCircleWidth;
    protected int mGraduateLength;
    protected int mGraduateWidth;
    protected int mHourWidth;
    protected int mMinuteWidth;
    protected int mSecondWidth;

    protected int mDefaultColor;
    protected int mCircleColor;
    protected int mGraduateColor;
    protected int mHourColor;
    protected int mMinuteColor;
    protected int mSecondColor;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initDefaultValue();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ClockView, defStyleAttr, defStyleRes);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = a.getIndex(i);
            if (index == R.styleable.ClockView_radius) {
                mRadius = a.getDimensionPixelSize(index, mRadius);
            } else if (index == R.styleable.ClockView_circle_width) {
                mCircleWidth = a.getDimensionPixelSize(index, mCircleWidth);
            } else if (index == R.styleable.ClockView_graduate_length) {
                mGraduateLength = a.getDimensionPixelSize(index, mGraduateLength);
            } else if (index == R.styleable.ClockView_graduate_width) {
                mGraduateWidth = a.getDimensionPixelSize(index, mGraduateWidth);
            } else if (index == R.styleable.ClockView_hour_width) {
                mHourWidth = a.getDimensionPixelSize(index, mHourWidth);
            } else if (index == R.styleable.ClockView_minute_width) {
                mMinuteWidth = a.getDimensionPixelSize(index, mMinuteWidth);
            } else if (index == R.styleable.ClockView_second_width) {
                mSecondWidth = a.getDimensionPixelSize(index, mSecondWidth);
            } else if (index == R.styleable.ClockView_default_color) {
                mDefaultColor = a.getColor(index, DEFAULT_COLOR);
            } else if (index == R.styleable.ClockView_circle_color) {
                mCircleColor = a.getColor(index, DEFAULT_COLOR);
            } else if (index == R.styleable.ClockView_graduate_color) {
                mGraduateColor = a.getColor(index, DEFAULT_COLOR);
            } else if (index == R.styleable.ClockView_hour_color) {
                mHourColor = a.getColor(index, DEFAULT_COLOR);
            } else if (index == R.styleable.ClockView_minute_color) {
                mMinuteColor = a.getColor(index, DEFAULT_COLOR);
            } else if (index == R.styleable.ClockView_second_color) {
                mSecondColor = a.getColor(index, DEFAULT_COLOR);
            }
        }
        a.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 设置扛锯齿
        mCenterPoint = new Point();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    protected void initDefaultValue() {
        mRadius = getDefaultValue(80);
        mCircleWidth = getDefaultValue(1);
        mGraduateLength = getDefaultValue(6);
        mGraduateWidth = getDefaultValue(1);
        mHourWidth = getDefaultValue(3);
        mMinuteWidth = getDefaultValue(2);
        mSecondWidth = getDefaultValue(1);
        mDefaultColor = DEFAULT_COLOR;
    }

    public void stop() {
        removeCallbacks(mRunnable);
    }

    @SuppressWarnings("unused")
    public void start() {
        invalidate();
        updateTime();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        if (specWidthMode == MeasureSpec.EXACTLY) {
            width = specWidthSize;
        } else {
            width = mRadius * 2 + getPaddingStart() + getPaddingEnd();
        }

        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        if (specHeightMode == MeasureSpec.EXACTLY) {
            height = specHeightSize;
        } else {
            height = mRadius * 2 + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int pointX = (left + right) / 2;
        int pointY = (top + bottom) / 2;
        mCenterPoint.set(pointX, pointY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
        drawGraduate(canvas);
        drawTime(canvas);
        updateTime();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    protected void drawCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(mCircleColor != 0 ? mCircleColor : mDefaultColor);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mRadius, mPaint);
    }

    protected void drawGraduate(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mGraduateWidth);
        mPaint.setColor(mGraduateColor != 0 ? mGraduateColor : mDefaultColor);

        final int totalAngle = 360;
        final int totalCount = 12;
        final int average = totalAngle / totalCount;
        for (int angle = 0; angle < totalAngle; angle += average) {
            double radian = Math.toRadians(angle);
            float startX = (float) (mCenterPoint.x + mRadius * Math.cos(radian));
            float startY = (float) (mCenterPoint.y - mRadius * Math.sin(radian));
            float stopX = (float) (mCenterPoint.x + (mRadius - mGraduateLength) * Math.cos(radian));
            float stopY = (float) (mCenterPoint.y - (mRadius - mGraduateLength) * Math.sin(radian));

            canvas.drawLine(startX, startY, stopX, stopY, mPaint);
        }
    }

    protected void drawTime(Canvas canvas) {
        drawHour(canvas);
        drawMinute(canvas);
        drawSecond(canvas);
    }

    protected void drawHour(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mHourWidth);
        mPaint.setColor(mHourColor != 0 ? mHourColor : mDefaultColor);

        final int hour = Calendar.getInstance().get(Calendar.HOUR);
        final int minute = Calendar.getInstance().get(Calendar.MINUTE);
        final double radian = getRadian(hour + minute / 60f, 12);
        final int hourNeedleLength = mRadius / 2;
        doDrawTime(canvas, hourNeedleLength, radian);
    }

    protected void drawMinute(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mMinuteWidth);
        mPaint.setColor(mMinuteColor != 0 ? mMinuteColor : mDefaultColor);

        final int minute = Calendar.getInstance().get(Calendar.MINUTE);
        final int second = Calendar.getInstance().get(Calendar.SECOND);
        final double radian = getRadian(minute + second / 60f, 60);
        final int minuteNeedleLength = mRadius - 2 * mGraduateLength;
        doDrawTime(canvas, minuteNeedleLength, radian);
    }

    protected void drawSecond(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mSecondWidth);
        mPaint.setColor(mSecondColor != 0 ? mSecondColor : mDefaultColor);

        final int second = Calendar.getInstance().get(Calendar.SECOND);
        final double radian = getRadian(second, 60);
        final int secondNeedleLength = mRadius - 2 * mGraduateLength;
        doDrawTime(canvas, secondNeedleLength, radian);
    }

    protected void doDrawTime(Canvas canvas, int length, double radian) {
        final float startX = mCenterPoint.x;
        final float startY = mCenterPoint.y;
        final float stopX = (float) (mCenterPoint.x + length * Math.cos(radian));
        final float stopY = (float) (mCenterPoint.y - length * Math.sin(radian));
        canvas.drawLine(startX, startY, stopX, stopY, mPaint);
    }

    protected double getRadian(float value, int totalValue) {
        final int totalAngle = 360;
        final float angle = 90 - (value * totalAngle / totalValue);
        return Math.toRadians(angle);
    }

    protected void updateTime() {
        removeCallbacks(mRunnable);
        postDelayed(mRunnable, DELAY_UPDATE);
    }

    private int getDefaultValue(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                getResources().getDisplayMetrics());
    }
}
