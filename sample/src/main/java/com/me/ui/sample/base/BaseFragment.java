package com.me.ui.sample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author tangqi on 16-12-29.
 */

public abstract class BaseFragment extends Fragment {

    protected static final String KEY_TITLE = "key_title";

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
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
