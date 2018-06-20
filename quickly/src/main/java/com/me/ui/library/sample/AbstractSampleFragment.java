package com.me.ui.library.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author tangqi on 16-12-29.
 */

public abstract class AbstractSampleFragment extends Fragment {

    public static final String KEY_TITLE = "key_title";
    protected Context mContext;

    protected abstract int getContentViewId();

    protected abstract void initView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getContentViewId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString(KEY_TITLE);
            setActionBarTitle(title);
        }
    }

    protected void setActionBarTitle(String title) {
        if (getActivity() != null) {
            getActivity().setTitle(title);
        }
    }

    protected void setActionBarTitle(int resId) {
        if (getActivity() != null) {
            getActivity().setTitle(resId);
        }
    }
}
