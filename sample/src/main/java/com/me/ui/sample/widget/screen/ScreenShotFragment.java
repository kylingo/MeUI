package com.me.ui.sample.widget.screen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.me.ui.library.util.CacheUtils;
import com.me.ui.library.util.ScreenShotUtils;
import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.widget.decoration.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面截屏，可以是长图
 *
 * @author kylingo on 18/6/22
 */
public class ScreenShotFragment extends BaseListFragment {

    private static final String TAG = "ScreenShotFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new LinearItemDecoration(getActivity());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_screen_shot, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_screen_draw:
                generateScreenShot(mRootView);
                return true;

            case R.id.menu_screen_list:
                generateRecyclerView();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 使用Canvas画
     */
    private void generateScreenShot(View rootView) {
        String filePath = CacheUtils.getScreenShotPath(mContext, "canvas");
        ScreenShotUtils.createScreenShot(rootView, filePath);
    }

    private void generateRecyclerView() {
        Bitmap bitmap = shotRecyclerView(mRecyclerView);
        String filePath = CacheUtils.getScreenShotPath(mContext, "recylcer");
        ScreenShotUtils.generateScreenFile(bitmap, filePath);
    }

    /**
     * 此方法，截图有问题
     */
    @Deprecated
    public static Bitmap shotRecyclerView(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        int itemsCount = adapter.getItemCount();
        int allItemsHeight = 0;
        List<Bitmap> bitmaps = new ArrayList<>();

        for (int i = 0; i < itemsCount; i++) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForLayoutPosition(i);
            if (viewHolder != null) {
                View childView = viewHolder.itemView;
                childView.measure(
                        View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
                childView.setDrawingCacheEnabled(true);
                childView.buildDrawingCache();

                bitmaps.add(childView.getDrawingCache());
                allItemsHeight += childView.getMeasuredHeight();
            }
        }
        int w = recyclerView.getMeasuredWidth();
        Bitmap bigBitmap = Bitmap.createBitmap(w, allItemsHeight, Bitmap.Config.ARGB_8888);
        Canvas bigCanvas = new Canvas(bigBitmap);

        Paint paint = new Paint();
        int iHeight = 0;

        for (int i = 0; i < bitmaps.size(); i++) {
            Bitmap bmp = bitmaps.get(i);
            bigCanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight += bmp.getHeight();

            bmp.recycle();
        }
        return bigBitmap;
    }
}
