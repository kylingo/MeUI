package com.me.ui.widget.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * @author studiotang on 17/3/29
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private static final int DEFAULT_DIVIDER_SIZE = 1;

    private int mOrientation = LinearLayoutManager.VERTICAL;

    private int mDividerSize;

    private Paint mPaint;

    public LinearItemDecoration(Context context) {
        mDividerSize = (int) TypedValue.applyDimension(DEFAULT_DIVIDER_SIZE,
                TypedValue.COMPLEX_UNIT_DIP, context.getResources().getDisplayMetrics());
    }

    public LinearItemDecoration setOrientation(int orientation) {
        this.mOrientation = orientation;
        if (orientation != LinearLayoutManager.VERTICAL
                && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("Invalid params orientation!");
        }
        return this;
    }

    public LinearItemDecoration setDividerSize(int dividerSize) {
        mDividerSize = dividerSize;
        return this;
    }

    public LinearItemDecoration setColor(int color) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        return this;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mPaint == null) {
            return;
        }

        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDividerSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDividerSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDividerSize);
        } else {
            outRect.set(0, 0, mDividerSize, 0);
        }
    }
}
