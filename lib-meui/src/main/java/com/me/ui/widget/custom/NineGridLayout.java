package com.me.ui.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.me.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangqi on 17-7-5.
 *         see {#https://github.com/Naoki2015/CircleDemo}
 *         see {#https://github.com/HMY314/NineGridLayout}
 */
public class NineGridLayout<T> extends LinearLayout {
    private ImageHolder<T> mImageHolder;
    private List<T> mImageList;
    private int mImagePadding;
    private int mMaxWidth;

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

        initDefaultValue();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NineGridLayout, defStyleAttr, defStyleRes);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = a.getIndex(i);
            if (index == R.styleable.NineGridLayout_image_padding) {
                mImagePadding = a.getDimensionPixelSize(index, mImagePadding);
            }
        }
        mImageList = new ArrayList<>();
    }

    protected void initDefaultValue() {
        mImagePadding = getDefaultValue(2f);
    }

    public void setData(List<T> list) {
        if (list != null) {
            mImageList.clear();
            mImageList.addAll(list);
            updateView();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        if (result > 0) {
            mMaxWidth = result;
            updateView();
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void updateView() {
        this.setOrientation(VERTICAL);
        this.removeAllViews();
        if (mMaxWidth == 0) {
            addView(new View(getContext()));
            return;
        }

        if (mImageList == null || mImageList.size() == 0) {
            return;
        }

        if (mImageList.size() == 1) {
            addSingleImageView();
        } else {
            addMultiImageView();
        }
    }

    private void addSingleImageView() {
        addView(createImageView(0, false, 1));
    }

    private void addMultiImageView() {
        int rowMaxColumn;
        int totalCount = mImageList.size();
        if (totalCount == 4) {
            rowMaxColumn = 2;
        } else {
            rowMaxColumn = 3;
        }
        int rowCount = totalCount / rowMaxColumn + (totalCount % rowMaxColumn > 0 ? 1 : 0);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        for (int rowCursor = 0; rowCursor < rowCount; rowCursor++) {
            LinearLayout rowLayout = new LinearLayout(getContext());
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setLayoutParams(layoutParams);

            if (rowCursor != 0) {
                rowLayout.setPadding(0, mImagePadding, 0, 0);
            }

            int columnCount = totalCount % rowMaxColumn == 0 ? rowMaxColumn
                    : totalCount % rowMaxColumn;
            if (rowCursor != rowCount - 1) {
                columnCount = rowMaxColumn;
            }
            addView(rowLayout);

            int rowOffset = rowCursor * rowMaxColumn;
            for (int columnCursor = 0; columnCursor < columnCount; columnCursor++) {
                int position = columnCursor + rowOffset;
                rowLayout.addView(createImageView(position, true, rowMaxColumn));
            }
        }
    }

    private ImageView createImageView(int position, final boolean isMultiImage, int rowMaxColumn) {
        ImageView imageView = new ImageView(getContext());
        if (isMultiImage) {
            int paddingCount = (rowMaxColumn - 1);
            int multiImageSize = (mMaxWidth - mImagePadding * paddingCount) / rowMaxColumn;
            int width;
            if (position % rowMaxColumn == rowMaxColumn - 1) {
                // 解决最后一列右边没有对齐问题
                width = mMaxWidth - (mMaxWidth - mImagePadding * paddingCount) *
                        paddingCount / rowMaxColumn;
            } else {
                width = multiImageSize;
            }

            LayoutParams layoutParams = new LayoutParams(width, multiImageSize);
            if (position % rowMaxColumn != 0) {
                layoutParams.setMargins(mImagePadding, 0, 0, 0);
            }

            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageView.setLayoutParams(layoutParams);
        } else {
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageView.setMaxWidth(mMaxWidth * 2 / 3);
            imageView.setAdjustViewBounds(true);
            imageView.setLayoutParams(layoutParams);
        }

        if (mImageHolder != null) {
            T t = mImageList.get(position);
            mImageHolder.onItemClick(t, imageView, position);
            mImageHolder.displayImage(t, imageView, position);
        }
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

    private int getDefaultValue(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                getResources().getDisplayMetrics());
    }
}
