package com.me.ui.library.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面截图工具类
 * 说明：如果是大图，且视图很长的情况下，需要分开截屏
 *
 * @author kylingo on 18/6/22
 */
public class ScreenShotUtils {

    /**
     * 截屏的最大高度，也可以限制bitmap总大小
     */
    private static final int MAX_HEIGHT = 1080 * 20;

    /**
     * 生成屏幕截图，最大为全屏
     *
     * @param view     目标视图
     * @param filePath 截图保存路径
     */
    public static boolean createScreenShot(View view, String filePath) {
        Bitmap bitmap = getBitmap(view);
        return generateScreenFile(bitmap, filePath);
    }

    /**
     * 生成屏幕截图，可滚动
     *
     * @param scrollView 滚动视图
     * @param filePath   截图保存路径
     */
    public static boolean createScreenShot(ScrollView scrollView, String filePath) {
        Bitmap bitmap = getScrollViewBitmap(scrollView);
        return generateScreenFile(bitmap, filePath);
    }

    /**
     * 生成屏幕截图，网页可滚动
     *
     * @param webView  网页视图
     * @param filePath 截图保存路径
     */
    public static boolean createScreenShot(WebView webView, String filePath) {
        Bitmap bitmap = getWebViewBitmap(webView);
        return generateScreenFile(bitmap, filePath);
    }

    /**
     * 生成屏幕截图，列表可滚动
     *
     * @param listView 列表视图
     * @param filePath 截图保存路径
     */
    public static boolean createScreenShot(ListView listView, String filePath) {
        Bitmap bitmap = getListViewBitmap(listView);
        return generateScreenFile(bitmap, filePath);
    }

    /**
     * 获取视图的bitmap
     */
    public static Bitmap getBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    /**
     * 获取视图缓存的bitmap
     */
    @Deprecated
    public static Bitmap getBitmapCache(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return Bitmap.createBitmap(view.getDrawingCache());
    }

    /**
     * 获取滚动视图的bitmap
     */
    public static Bitmap getScrollViewBitmap(ScrollView scrollView) {
        int height = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            height += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        height = Math.min(height, MAX_HEIGHT);
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), height,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 获取网页的bitmap
     */
    public static Bitmap getWebViewBitmap(WebView webView) {
        float scale = webView.getScale();
        int height = (int) (webView.getContentHeight() * scale);
        height = Math.min(height, MAX_HEIGHT);
        Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), height, Bitmap.Config.RGB_565);
        webView.draw(new Canvas(bitmap));
        return bitmap;
    }

    /**
     * 获取列表的bitmap
     */
    public static Bitmap getListViewBitmap(ListView listview) {
        ListAdapter adapter = listview.getAdapter();
        int itemsCount = adapter.getCount();
        int allItemsHeight = 0;
        List<Bitmap> bitmaps = new ArrayList<>();

        for (int i = 0; i < itemsCount; i++) {
            View childView = adapter.getView(i, null, listview);

            childView.measure(
                    View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();

            bitmaps.add(childView.getDrawingCache());
            allItemsHeight += childView.getMeasuredHeight();
        }
        int w = listview.getMeasuredWidth();
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

    /**
     * 生成截图文件
     */
    public static boolean generateScreenFile(Bitmap bitmap, String filePath) {
        if (bitmap == null || TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(filePath);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}
