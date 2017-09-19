# ItemDecoration
Android RecyclerView item decoration

## GridItemDecoration
自定义RecyclerView网格布局的外间距和内间距，保证各个Item是等宽的。

核心代码
```Java
 protected void calculateOffset(Offset offset, int spanCount, int spanIndex, int spanSize, Rect outRect) {
    if (offset == null) {
        return;
    }

    int realIndex = spanIndex / spanSize;
    int elementCount = spanCount / spanSize;

    // 算出每个Item平均的offset
    int totalOffset = (2 * offset.outside + (elementCount - 1) * offset.inside) / elementCount;
    // 根据公式计算出的每个item的左边距
    outRect.left = offset.outside + (realIndex * (offset.inside - 2 * offset.outside)) / elementCount;
    // 右边距 = Item总偏移 - 左边距
    outRect.right = totalOffset - outRect.left;

    if (offset.top != 0) {
        outRect.top = offset.top;
    }

    if (offset.bottom != 0) {
        outRect.bottom = offset.bottom;
    }
 }
```

示例用法：
```Java
public class MainItemDecoration extends GridItemDecoration {
    private Context mContext;

    public MainItemDecoration(Context context) {
        mContext = context;
    }

    @Override
    protected Offset filterViewType(int viewType) {
        Offset offset = new Offset();
        offset.outside = mContext.getResources().getDimensionPixelOffset(R.dimen.main_item_outside);
        offset.inside = mContext.getResources().getDimensionPixelOffset(R.dimen.main_item_inside);
        offset.top = 0;
        offset.bottom = mContext.getResources().getDimensionPixelOffset(R.dimen.main_item_bottom);
        return offset;
    }
}
```

## LinearItemDecoration
自定义RecyclerView的线性间距
```Java
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
```