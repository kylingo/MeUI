package com.me.ui.app.wanandroid.page.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.me.ui.app.R;

/**
 * @author kylingo
 * @since 2019/05/07 12:56
 */
public class WanTitleView extends FrameLayout {

    private TextView mTvTitle;

    public WanTitleView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public WanTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WanTitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_wan_title, this);
        mTvTitle = view.findViewById(R.id.tv_wan_title);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }
}
