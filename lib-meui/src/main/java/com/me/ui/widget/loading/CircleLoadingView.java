package com.me.ui.widget.loading;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author tangqi on 17-7-24.
 */
public class CircleLoadingView extends View {

    private static final int ROTATE_DURATION = 800;
    private static final int TOTAL_ANGLE = 360;
    private static final int COUNT = 8;
    private static final String[] DEFAULT_COLORS = {
            "#FA6762", "#F5C414", "#EFD642", "#B1EB42",
            "#55F151", "#55E2E9", "#3CB5EB", "#6C5EED"
    };

    private ObjectAnimator mRotateAnimation;
    private Paint mPaint;
    private Point mCenterPoint;
    private int mRadius = 50;
    private int mRadiusWidth = 6;
    private int mInsideRadius = 15;
    private int mInsideWidth = 6;
    private int mTangentWidth = 6;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mCenterPoint = new Point();
    }

    public void start() {
        mRotateAnimation = getRotateAnim();
        mRotateAnimation.start();
    }

    public void stop() {
        if (mRotateAnimation != null) {
            mRotateAnimation.cancel();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        if (specWidthMode == MeasureSpec.EXACTLY) {
            width = specWidthSize;
        } else {
            width = mRadius * 2 + getPaddingLeft() + getPaddingRight();
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

        int centerX = (left + right) / 2;
        int centerY = (top + bottom) / 2;
        mCenterPoint.set(centerX, centerY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
        drawInsideCircle(canvas);
        drawTangent(canvas);
    }

    private void drawCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRadiusWidth);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mRadius, mPaint);
    }

    private void drawInsideCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mInsideWidth);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mInsideRadius, mPaint);
    }

    /**
     * 画切线
     */
    private void drawTangent(Canvas canvas) {
        final int averageAngle = TOTAL_ANGLE / COUNT;
        double tangentAngle = Math.acos((mInsideRadius / (float) mRadius));

        Line firstLine = new Line();
        Line lastLine = new Line();
        Line line = new Line();
        for (int angle = 0; angle < TOTAL_ANGLE; angle += averageAngle) {
            float radian = (float) Math.toRadians(angle);
            float startX = (float) (mCenterPoint.x + mInsideRadius * Math.cos(radian));
            float startY = (float) (mCenterPoint.y - mInsideRadius * Math.sin(radian));
            float stopX = (float) (mCenterPoint.x + mRadius * Math.cos(tangentAngle));
            float stopY = (float) (mCenterPoint.y - mRadius * Math.sin(tangentAngle));

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mTangentWidth);
            mPaint.setColor(Color.WHITE);
            canvas.drawLine(startX, startY, stopX, stopY, mPaint);

            line.set(startX, startY, stopX, stopY);
            if (angle != 0) {
                int index = angle / averageAngle;
                int startAngle = (int) (TOTAL_ANGLE - Math.toDegrees(tangentAngle));
                drawArc(canvas, line, lastLine, index, startAngle, averageAngle);

                // 最后一个切线和第一个切线的区域
                if (angle == TOTAL_ANGLE - averageAngle) {
                    index = 0;
                    tangentAngle += Math.toRadians(averageAngle);
                    startAngle = (int) (TOTAL_ANGLE - Math.toDegrees(tangentAngle));
                    drawArc(canvas, firstLine, line, index, startAngle, averageAngle);
                }
            } else {
                firstLine.set(startX, startY, stopX, stopY);
            }
            lastLine.set(startX, startY, stopX, stopY);

            tangentAngle += Math.toRadians(averageAngle);
        }
    }

    /**
     * 填充扇形
     */
    private void drawArc(Canvas canvas, Line line, Line lastLine, int index,
                         int angle, int sweepAngle) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mRadiusWidth);
        int color = getArcColor(index);
        if (color != 0) {
            mPaint.setColor(color);
        }

        Path path = new Path();
        path.moveTo(line.stopX, line.stopY);
        path.lineTo(line.startX, line.startY);
        path.lineTo(lastLine.startX, lastLine.startY);
        path.lineTo(lastLine.stopX, lastLine.stopY);
        RectF oval = new RectF(mCenterPoint.x - mRadius,
                mCenterPoint.y - mRadius,
                mCenterPoint.x + mRadius,
                mCenterPoint.y + mRadius);
        path.addArc(oval, angle, sweepAngle);
        path.close();

        canvas.drawPath(path, mPaint);
    }

    private int getArcColor(int index) {
        int color;
        if (index < DEFAULT_COLORS.length) {
            String colorString = DEFAULT_COLORS[index];
            color = Color.parseColor(colorString);
        } else {
            color = Color.RED;
        }

        return color;
    }

    private ObjectAnimator  getRotateAnim() {
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotation",
                0, 360);
        ObjectAnimator rotateAnim = ObjectAnimator.ofPropertyValuesHolder(this, rotation)
                .setDuration(ROTATE_DURATION);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.setInterpolator(new LinearInterpolator());
        return rotateAnim;
    }

    private static class Line {
        float startX;
        float startY;
        float stopX;
        float stopY;

        void set(float startX, float startY, float stopX, float stopY) {
            this.startX = startX;
            this.startY = startY;
            this.stopX = stopX;
            this.stopY = stopY;
        }
    }
}
