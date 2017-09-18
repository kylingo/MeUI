package com.me.ui.widget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author tangqi on 17-6-19.
 */
public abstract class GridItemDecoration extends RecyclerView.ItemDecoration {

    // Return the viewType to draw offset
    protected abstract Offset filterViewType(int viewType);

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int position = parent.getChildAdapterPosition(view);
        final int viewType = parent.getAdapter().getItemViewType(position);
        Offset offset = filterViewType(viewType);
        if (offset != null) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
            final int spanSize = spanSizeLookup.getSpanSize(position);
            final int spanCount = layoutManager.getSpanCount();
            final int spanIndex = spanSizeLookup.getSpanIndex(position, spanCount);
            if (spanSize < 1 || spanSize > spanCount) {
                return;
            }

            calculateOffset(offset, spanCount, spanIndex, spanSize, outRect);

            return;
        }

        super.getItemOffsets(outRect, view, parent, state);
    }

    protected void calculateOffset(Offset offset, int spanCount, int spanIndex, int spanSize, Rect outRect) {
        if (offset == null) {
            return;
        }

        int realIndex = spanIndex / spanSize;
        int elementCount = spanCount / spanSize;
        int totalOffset = (2 * offset.outside + (elementCount - 1) * offset.inside) / elementCount;
        outRect.left = offset.outside + (realIndex * (offset.inside - 2 * offset.outside)) / elementCount;
        outRect.right = totalOffset - outRect.left;

        if (offset.top != 0) {
            outRect.top = offset.top;
        }

        if (offset.bottom != 0) {
            outRect.bottom = offset.bottom;
        }
    }

    public static class Offset {
        public int outside;
        public int inside;
        public int top;
        public int bottom;

        public Offset() {
            // Default construction
        }

        public Offset(int outside, int inside, int top, int bottom) {
            this.outside = outside;
            this.inside = inside;
            this.top = top;
            this.bottom = bottom;
        }
    }
}
