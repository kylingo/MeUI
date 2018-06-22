package com.me.ui.widget.navigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 底部导航视图
 *
 * @author kylingo on 18/6/22
 */
public class BottomNavigationView extends FrameLayout {

    public BottomNavigationView(@NonNull Context context) {
        this(context, null);
    }

    public BottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {

    }

    public void addNavigationItem() {

    }
}
