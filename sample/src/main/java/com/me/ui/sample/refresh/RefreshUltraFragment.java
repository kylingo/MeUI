package com.me.ui.sample.refresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;

/**
 * Created by tangqi on 16-12-30.
 */

public class RefreshUltraFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_refresh_ultra, container, false);
        return rootView;
    }
}
