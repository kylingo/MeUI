package com.me.ui.library.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.me.ui.quickly.R;


/**
 * @author tangqi on 16-12-29.
 */

public abstract class SampleListFragment extends SampleFragment<FragmentBean> {

    @Override
    protected void onClickItem(FragmentBean item) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) item.clazz.newInstance();

            Bundle bundle = new Bundle();
            bundle.putString(KEY_TITLE, item.title);
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            showFragment(fragment);
        }
    }

    protected void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_sample, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
