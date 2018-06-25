package com.me.ui.sample.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.me.ui.sample.R;

/**
 * @author kylingo on 18/6/25
 */
public class TitleFragment extends BaseFragment {

    private static final String KEY_TITLE = "key_title";

    private TextView mTvTitle;
    private String mTitle;

    public static TitleFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        TitleFragment fragment = new TitleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_title;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(KEY_TITLE);
        }

        mTvTitle = view.findViewById(R.id.tv_fragment_title);
        if (!TextUtils.isEmpty(mTitle)) {
            updateTitleView(mTitle);
        }
    }

    public void setTitleContent(String title) {
        mTitle = title;
        updateTitleView(title);
    }

    private void updateTitleView(String title) {
        mTvTitle.setText(title);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (!TextUtils.isEmpty(mTitle)) {
                setActionBarTitle(mTitle);
            }
        }
    }
}
