package com.me.ui.widget.navigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.me.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部导航视图
 *
 * @author kylingo on 18/6/22
 */
public class BottomNavigationView extends FrameLayout implements View.OnClickListener {

    protected LinearLayout mTabLinearLayout;
    protected List<BottomNavigationTabView> mTabViews;
    protected OnTabChangeListener mOnTabChangeListener;

    public interface OnTabChangeListener {
        void onTabChange(int position);
    }

    public BottomNavigationView(@NonNull Context context) {
        this(context, null);
    }

    public BottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    protected void initView(Context context) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.layout_bottom_navigation, this);
        mTabLinearLayout = rootView.findViewById(R.id.ll_bottom_navigation);
        mTabViews = new ArrayList<>();
    }

    public void addNavigationItem(List<NavigationItem> navigationItems) {
        mTabLinearLayout.removeAllViews();
        mTabViews.clear();

        if (navigationItems != null && navigationItems.size() > 0) {
            for (int i = 0; i < navigationItems.size(); i++) {
                NavigationItem navigationItem = navigationItems.get(i);
                BottomNavigationTabView tabView = createTabView(i, navigationItem);
                addTabView(tabView);
            }
        }
    }

    protected BottomNavigationTabView createTabView(int position, NavigationItem navigationItem) {
        BottomNavigationTabView tabView = new BottomNavigationTabView(getContext());
        String content = navigationItem.getContent();
        if (!TextUtils.isEmpty(content)) {
            tabView.setContent(content);
        }

        int resId = navigationItem.getResId();
        if (resId != 0) {
            tabView.setImageResource(resId);
        }

        if (position == 0) {
            tabView.setSelected(true);
        }

        tabView.setTag(position);
        tabView.setOnClickListener(this);
        return tabView;
    }

    protected void addTabView(BottomNavigationTabView tabView) {
        mTabViews.add(tabView);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        tabView.setLayoutParams(lp);
        mTabLinearLayout.addView(tabView);
    }

    public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        mOnTabChangeListener = onTabChangeListener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        updateTab(position);

        if (mOnTabChangeListener != null) {
            mOnTabChangeListener.onTabChange(position);
        }
    }

    protected void updateTab(int position) {
        for (BottomNavigationTabView tabView : mTabViews) {
            int tabIndex = (int) tabView.getTag();
            if (position == tabIndex) {
                tabView.setSelected(true);
            } else {
                tabView.setSelected(false);
            }
        }
    }
}
