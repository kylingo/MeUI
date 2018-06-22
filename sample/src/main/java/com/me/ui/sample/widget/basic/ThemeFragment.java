package com.me.ui.sample.widget.basic;

import android.view.View;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.me.ui.sample.support.util.ThemeUtils;

/**
 * @author kylingo on 18/6/22
 */
public class ThemeFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_theme;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btn_theme_light).setOnClickListener(this);
        view.findViewById(R.id.btn_theme_dark).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_theme_light:
                ThemeUtils.changeTheme(getActivity(), true);
                ThemeUtils.reCreate(getActivity());
                break;

            case R.id.btn_theme_dark:
                ThemeUtils.changeTheme(getActivity(), false);
                ThemeUtils.reCreate(getActivity());
                break;
        }
    }
}
