package com.me.ui.sample.widget.decoration.grid;

import android.content.Context;

import com.me.ui.sample.R;
import com.me.ui.widget.decoration.GridItemDecoration;

/**
 * @author studiotang on 17/6/19
 */
public class GridColorDecoration extends GridItemDecoration {
    private Context mContext;

    public GridColorDecoration(Context context) {
        mContext = context;
    }

    @Override
    protected GridItemDecoration.Offset filterViewType(int viewType) {
        Offset offset = new Offset();
        offset.outside = mContext.getResources().getDimensionPixelOffset(R.dimen.main_item_outside);
        offset.inside = mContext.getResources().getDimensionPixelOffset(R.dimen.main_item_inside);
        offset.top = 0;
        offset.bottom = mContext.getResources().getDimensionPixelOffset(R.dimen.main_item_bottom);
        return offset;
    }
}
