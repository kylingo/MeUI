package com.me.ui.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangqi on 17-7-5.
 */
public class NineGridLayout<T> extends ViewGroup {

    private List<T> mDatas;

    private void setData(List<T> list) {
        if (list != null) {
            mDatas.addAll(list);
            requestLayout();
        }
    }

    public NineGridLayout(Context context) {
        this(context, null);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NineGridLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mDatas = new ArrayList<>();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
