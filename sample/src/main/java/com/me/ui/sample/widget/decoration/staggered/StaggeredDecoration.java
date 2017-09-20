package com.me.ui.sample.widget.decoration.staggered;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    17/9/20 下午10:14
 */
public class StaggeredDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public StaggeredDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;
    }
}