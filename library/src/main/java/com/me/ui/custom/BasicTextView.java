package com.me.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.me.ui.R;

/**
 * @author tangqi on 17-3-9.
 */
public class BasicTextView extends TextView {

    protected static final int DEFAULT_TEXT_SIZE = 15;

    protected String mText;
    protected int mTextColor;
    protected int mTextSize;

    protected Paint mPaint;
    protected Rect mRect;

    public BasicTextView(Context context) {
        this(context, null);
    }

    public BasicTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.BasicTextView, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);

            if (attr == R.styleable.BasicTextView_basicText) {
                mText = a.getString(attr);
            } else if (attr == R.styleable.BasicTextView_basicTextColor) {
                mTextColor = a.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.BasicTextView_basicTextSize) {
                mTextSize = a.getDimensionPixelSize(attr, getDefaultTextSize());
            }
        }
        a.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);

        mRect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mRect);
            float textWidth = mRect.width();
            width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
        }

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mRect);
            float textHeight = mRect.height();
            height = (int) (getPaddingTop() + textHeight + getPaddingBottom());
        }

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTextColor);
        canvas.drawText(mText, getWidth() / 2 - mRect.width() / 2,
                getHeight() / 2 + mRect.height() / 2, mPaint);
    }

    private int getDefaultTextSize() {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE,
                getResources().getDisplayMetrics());
    }
}
