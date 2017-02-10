package com.me.ui.sample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by tangqi on 16-12-29.
 */

public class BaseFragment extends Fragment {

    protected static final String KEY_TITLE = "key_title";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString(KEY_TITLE);
            getActivity().setTitle(title);
        }
    }
}
