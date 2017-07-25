package com.me.ui.widget.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author tangqi on 17-7-24.
 */
public class CircleLoadingView extends View {

    private int mRadius = 50;
    private int mInsideRadius = 15;

    private Paint mPaint;
    private Point mCenterPoint;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mCenterPoint = new Point();
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
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mRadius, mPaint);
    }

    private void drawInsideCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mInsideRadius, mPaint);
    }

    private void drawTangent(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.WHITE);

        final int totalAngle = 360;
        final int count = 8;
        final int averageAngle = totalAngle / count;
        double tangentAngle = Math.acos((mInsideRadius / (float) mRadius));

        Line lastLine = new Line();
        Line line = new Line();
        for (int angle = 0; angle < totalAngle; angle += averageAngle) {
            float radian = (float) Math.toRadians(angle);
            float startX = (float) (mCenterPoint.x + mInsideRadius * Math.cos(radian));
            float startY = (float) (mCenterPoint.y - mInsideRadius * Math.sin(radian));
            float stopX = (float) (mCenterPoint.x + mRadius * Math.cos(tangentAngle));
            float stopY = (float) (mCenterPoint.y - mRadius * Math.sin(tangentAngle));
            canvas.drawLine(startX, startY, stopX, stopY, mPaint);

            line.set(startX, startY, stopX, stopY);
//            if (angle != 0) {
//                int index = angle / averageAngle;
//                drawArc(canvas, line, lastLine, index, angle + 90);
//            }
            lastLine.set(startX, startY, stopX, stopY);

            tangentAngle += Math.toRadians(averageAngle);
        }
    }

    private void drawArc(Canvas canvas, Line line, Line lastLine, int index, int angle) {
        mPaint.setStyle(Paint.Style.FILL);
        int color = getArcColor(index);
        if (color != 0) {
            mPaint.setColor(color);
        }

        float radian = (float) Math.toRadians(angle);
        float sweepRadian = (float) Math.toRadians(45);
        float left = Math.min(Math.min(line.startX, line.stopX),
                Math.min(lastLine.startX, lastLine.stopX));
        float right = Math.max(Math.max(line.startX, line.stopX),
                Math.max(lastLine.startX, lastLine.stopX));
        float top = Math.min(Math.min(line.startY, line.stopY),
                Math.min(lastLine.startY, lastLine.stopY));
        float bottom = Math.max(Math.max(line.startY, line.stopY),
                Math.max(lastLine.startY, lastLine.stopY));
        canvas.drawArc(left, top, right, bottom, radian, sweepRadian, true, mPaint);
//
//        Path path = new Path();
//        path.moveTo(line.startX, line.startY);
//        path.lineTo(line.stopX, line.stopY);
//
//        Path lastPath = new Path();
//        lastPath.moveTo(lastLine.startX, lastLine.startY);
//        lastPath.lineTo(lastLine.stopX, lastLine.stopY);
//        path.addPath(lastPath);
//        canvas.clipPath(path);
//        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mRadius, mPaint);
    }

    private int getArcColor(int index) {
        int color;
        switch (index) {
            case 0:
                color = Color.YELLOW;
                break;

            case 1:
                color = Color.CYAN;
                break;

            case 2:
                color= Color.GREEN;
                break;

            case 3:
                color = Color.RED;
                break;

            case 4:
                color = Color.BLUE;
                break;

            case 5:
                color = Color.MAGENTA;
                break;

            case 6:
                color = Color.DKGRAY;
                break;

            case 7:
                color = Color.LTGRAY;
                break;

            default:
                color = Color.RED;
        }

        return color;
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
