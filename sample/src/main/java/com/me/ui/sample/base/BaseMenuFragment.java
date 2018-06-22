package com.me.ui.sample.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * 单个菜单页面抽象类
 *
 * @author kylingo on 18/6/22
 */
public abstract class BaseMenuFragment extends BaseFragment {

    private static final String MENU_TITLE = "Action";

    protected abstract void onMenuClick(MenuItem item);

    /**
     * 自定义菜单标题
     */
    @NonNull
    protected String getMenuTitle() {
        return MENU_TITLE;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(getMenuTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (getMenuTitle().equals(item.getTitle())) {
            onMenuClick(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
