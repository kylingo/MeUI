package com.me.ui.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangqi on 17-7-5.
 *         see {#https://github.com/Naoki2015/CircleDemo}
 *         see {#https://github.com/HMY314/NineGridLayout}
 */
public class NineGridLayout<T> extends LinearLayout {

    public int MAX_WIDTH = 0;

    private int MAX_PER_ROW_COUNT = 3;// 每行显示最大数
    private int mPxImagePadding = 12;// 图片间的间距

    private int mPxOneMaxWandH;  // 单张图最大允许宽高
    private int mPxMoreWandH = 0;// 多张图的宽高

    private LayoutParams mOnePicPara;
    private LayoutParams mMorePara, mMoreParaColumnFirst;
    private LayoutParams mRowPara;

    private ImageHolder<T> mImageHolder;

    private List<T> mImageList;

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
        mImageList = new ArrayList<>();
    }

    public void setData(List<T> list) {
        if (list != null) {
            mImageList.addAll(list);
            requestLayout();
        }
    }

    public void setList(List<T> lists) {
        if (lists == null) {
            return;
        }

        mImageList = lists;

        if (MAX_WIDTH > 0) {
            mPxMoreWandH = (MAX_WIDTH - mPxImagePadding * 2) / 3; //解决右侧图片和内容对不齐问题
            mPxOneMaxWandH = MAX_WIDTH * 2 / 3;
            initImageLayoutParams();
        }

        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MAX_WIDTH == 0) {
            int width = measureWidth(widthMeasureSpec);
            if (width > 0) {
                MAX_WIDTH = width;
                if (mImageList != null && mImageList.size() > 0) {
                    setList(mImageList);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            // result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
            // + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private void initImageLayoutParams() {
        int wrap = LayoutParams.WRAP_CONTENT;
        int match = LayoutParams.MATCH_PARENT;

        mOnePicPara = new LayoutParams(wrap, wrap);

        mMoreParaColumnFirst = new LayoutParams(mPxMoreWandH, mPxMoreWandH);
        mMorePara = new LayoutParams(mPxMoreWandH, mPxMoreWandH);
        mMorePara.setMargins(mPxImagePadding, 0, 0, 0);

        mRowPara = new LayoutParams(match, wrap);
    }

    // 根据imageView的数量初始化不同的View布局,还要为每一个View作点击效果
    private void initView() {
        this.setOrientation(VERTICAL);
        this.removeAllViews();
        if (MAX_WIDTH == 0) {
            //为了触发onMeasure()来测量MultiImageView的最大宽度，MultiImageView的宽设置为match_parent
            addView(new View(getContext()));
            return;
        }

        if (mImageList == null || mImageList.size() == 0) {
            return;
        }

        if (mImageList.size() == 1) {
            addView(createImageView(0, false));
        } else {
            int allCount = mImageList.size();
            if (allCount == 4) {
                MAX_PER_ROW_COUNT = 2;
            } else {
                MAX_PER_ROW_COUNT = 3;
            }
            int rowCount = allCount / MAX_PER_ROW_COUNT
                    + (allCount % MAX_PER_ROW_COUNT > 0 ? 1 : 0);// 行数
            for (int rowCursor = 0; rowCursor < rowCount; rowCursor++) {
                LinearLayout rowLayout = new LinearLayout(getContext());
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);

                rowLayout.setLayoutParams(mRowPara);
                if (rowCursor != 0) {
                    rowLayout.setPadding(0, mPxImagePadding, 0, 0);
                }

                int columnCount = allCount % MAX_PER_ROW_COUNT == 0 ? MAX_PER_ROW_COUNT
                        : allCount % MAX_PER_ROW_COUNT;//每行的列数
                if (rowCursor != rowCount - 1) {
                    columnCount = MAX_PER_ROW_COUNT;
                }
                addView(rowLayout);

                int rowOffset = rowCursor * MAX_PER_ROW_COUNT;// 行偏移
                for (int columnCursor = 0; columnCursor < columnCount; columnCursor++) {
                    int position = columnCursor + rowOffset;
                    rowLayout.addView(createImageView(position, true));
                }
            }
        }
    }

    private ImageView createImageView(int position, final boolean isMultiImage) {
        T t = mImageList.get(position);
        ImageView imageView = new ImageView(getContext());
        if (isMultiImage) {
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageView.setLayoutParams(position % MAX_PER_ROW_COUNT == 0 ? mMoreParaColumnFirst : mMorePara);
        } else {
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            //imageView.setMaxHeight(pxOneMaxWandH);

            // To get image origin size from T
            int expectW = 0;
            int expectH = 0;

            //noinspection ConstantConditions
            if (expectW == 0 || expectH == 0) {
                imageView.setLayoutParams(mOnePicPara);
            } else {
                // TODO show image origin size
                int actualW;
                int actualH;
                float scale = ((float) expectH) / ((float) expectW);
                if (expectW > mPxOneMaxWandH) {
                    actualW = mPxOneMaxWandH;
                    actualH = (int) (actualW * scale);
                } else if (expectW < mPxMoreWandH) {
                    actualW = mPxMoreWandH;
                    actualH = (int) (actualW * scale);
                } else {
                    actualW = expectW;
                    actualH = expectH;
                }
                imageView.setLayoutParams(new LayoutParams(actualW, actualH));
            }
        }

        if (mImageHolder != null) {
            mImageHolder.onItemClick(t, imageView, position);
            mImageHolder.displayImage(t, imageView, position);
        }

//        imageView.setId(photoInfo.url.hashCode());
//        imageView.setBackgroundColor(getResources().getColor(R.color.im_font_color_text_hint));
        return imageView;
    }

    public interface ImageHolder<T> {
        void displayImage(T t, ImageView imageView, int position);

        void onItemClick(T t, View view, int position);
    }

    @SuppressWarnings("unchecked")
    public void setImageHolder(ImageHolder<T> imageHolder) {
        mImageHolder = imageHolder;
    }
}
