package com.me.ui.widget.refresh;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {
    protected RecyclerView recyclerView;
    private int dividerHeight;
    private boolean hasTopDivider;
    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            if (getHeaderSize() <= 0) {
                handler.postDelayed(this, 1);
            } else {
                handler.removeCallbacks(this);
                setRefreshing();
            }
        }
    };

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }
    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }
    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }
    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }
    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        recyclerView = new RecyclerView(context, attrs);
        //recyclerView.setId(R.id.recyclerview);
        return recyclerView;
    }
    @Override
    protected boolean isReadyForPullStart() {
        if (isRefreshing()) {
            return false;
        }
        if (mRefreshableView.getChildCount() <= 0)
            return true;
        int firstVisiblePosition = mRefreshableView.getChildPosition(mRefreshableView.getChildAt(0));
        if (firstVisiblePosition == 0){
            if(hasTopDivider){
                return mRefreshableView.getChildAt(0).getTop() == mRefreshableView.getPaddingTop()+ dividerHeight;
            }
            return mRefreshableView.getChildAt(0).getTop() == mRefreshableView.getPaddingTop();
        }
        else
            return false;
    }
    @Override
    protected boolean isReadyForPullEnd() {
        int lastVisiblePosition = mRefreshableView.getChildPosition(mRefreshableView.getChildAt(mRefreshableView.getChildCount() -1));
        if (lastVisiblePosition >= mRefreshableView.getAdapter().getItemCount()-1) {
            return mRefreshableView.getChildAt(mRefreshableView.getChildCount() - 1).getBottom() <= mRefreshableView.getBottom();
        }
        return false;
    }

    public RecyclerView getContentView() {
        return getRefreshableView();
    }

    public void setDividerHeight(int dividerHeight,boolean hasTopDivider){
        this.dividerHeight = dividerHeight;
        this.hasTopDivider = hasTopDivider;
    }

    public void startRefreshing() {
        if (getHeaderSize() <= 0) {
            handler.postDelayed(run, 1);
        } else {
            setRefreshing();
        }
    }

    public static class SimpleOnRefreshListener implements OnRefreshListener2<RecyclerView> {

        @Override
        public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

        }
    }
}
