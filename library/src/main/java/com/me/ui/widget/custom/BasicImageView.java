package com.me.ui.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.me.ui.R;

/**
 * @author tangqi on 17-3-9.
 */
public class BasicImageView extends View {

    protected static final int DEFAULT_TEXT_SIZE = 15;
    protected static final int SCALE_TYPE_FILL = 0;
    protected static final int SCALE_TYPE_CENTER = 1;
    protected static final int DEFAULT_SCALE_TYPE = SCALE_TYPE_FILL;

    protected String mText;
    protected int mTextColor;
    protected int mTextSize;
    protected Bitmap mImage;
    protected int mScaleType;

    protected int mWidth;
    protected int mHeight;
    protected Paint mPaint;
    protected Rect mRect;
    protected Rect mTextRect;

    public BasicImageView(Context context) {
        this(context, null);
    }

    public BasicImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.BasicImageView, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);

            if (attr == R.styleable.BasicImageView_imageText) {
                mText = a.getString(attr);
            } else if (attr == R.styleable.BasicImageView_imageTextColor) {
                mTextColor = a.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.BasicImageView_imageTextSize) {
                mTextSize = a.getDimensionPixelSize(attr, getDefaultTextSize());
            } else if (attr == R.styleable.BasicImageView_image) {
                mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
            } else if (attr == R.styleable.BasicImageView_imageScaleType) {
                mScaleType = a.getInt(attr, DEFAULT_SCALE_TYPE);
            }
        }
        a.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);

        mRect = new Rect();
        mTextRect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specWidthMode == MeasureSpec.EXACTLY) {
            mWidth = specWidthSize;
        } else if (specWidthMode == MeasureSpec.AT_MOST) {
            int tempImgWidth = mImage.getWidth() + getPaddingLeft() + getPaddingRight();
            int tempTitleWidth = mTextRect.width() + getPaddingLeft() + getPaddingRight();

            int tempWidth = Math.max(tempImgWidth, tempTitleWidth);
            mWidth = Math.min(tempWidth, specWidthSize);
        }

        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specHeightMode == MeasureSpec.EXACTLY) {
            mHeight = specHeightSize;
        } else if (specHeightMode == MeasureSpec.AT_MOST) {
            int tempHeight = mImage.getHeight() + mTextRect.height()
                    + getPaddingTop() + getPaddingBottom();
            mHeight = Math.min(tempHeight, specHeightSize);
        }

        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mRect.left = getPaddingLeft();
        mRect.right = mWidth - getPaddingRight();
        mRect.top = getPaddingTop();
        mRect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        if (mTextRect.width() > mWidth) {
            TextPaint textPaint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mText, textPaint,
                    mWidth - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            canvas.drawText(mText, mWidth / 2 - mTextRect.width() / 2, mHeight - getPaddingBottom(), mPaint);
        }

        mRect.bottom -= mTextRect.height();
        if (mScaleType == SCALE_TYPE_FILL) {
            canvas.drawBitmap(mImage, null, mRect, mPaint);
        } else {
            mRect.left = mWidth / 2 - mImage.getWidth() / 2;
            mRect.right = mWidth / 2 + mImage.getWidth() / 2;
            mRect.top = mHeight / 2 - mImage.getHeight() / 2 - mTextRect.height() / 2;
            mRect.bottom = mHeight / 2 + mImage.getHeight() / 2 - mTextRect.height() / 2;
            canvas.drawBitmap(mImage, null, mRect, mPaint);
        }
    }

    private int getDefaultTextSize() {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE,
                getResources().getDisplayMetrics());
    }
}
