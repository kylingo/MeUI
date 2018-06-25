package com.me.ui.widget.navigation;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.ui.R;

/**
 * @author kylingo on 18/6/25
 */
public class BottomNavigationTabView extends FrameLayout {

    protected ImageView mImageView;
    protected TextView mTextView;

    public BottomNavigationTabView(@NonNull Context context) {
        this(context, null);
    }

    public BottomNavigationTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    protected void initView(Context context) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.layout_bottom_navigation_tab, this);
        mImageView = rootView.findViewById(R.id.iv_navigation_tab);
        mTextView = rootView.findViewById(R.id.tv_navigation_tab);
    }

    public void setImageResource(@DrawableRes int resId) {
        mImageView.setImageResource(resId);
    }

    public void setContent(String content) {
        mTextView.setText(content);
    }
}
