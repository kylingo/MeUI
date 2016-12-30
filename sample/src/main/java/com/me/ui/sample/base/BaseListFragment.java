package com.me.ui.sample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.me.ui.sample.R;
import com.me.ui.sample.bean.FragmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangqi on 16-12-29.
 */

public abstract class BaseListFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    protected FrameLayout mFrameLayout;
    protected ArrayAdapter<FragmentBean> mArrayAdapter;

    protected abstract void addFragment(List<FragmentBean> fragmentBeans);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.fl_base);
        addView();
        return view;
    }

    protected int getContentViewId() {
        return R.layout.fragment_base;
    }

    protected void addView() {
        ListView listView = new ListView(getActivity());
        List<FragmentBean> fragmentBeans = new ArrayList<>();
        addFragment(fragmentBeans);

        mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, fragmentBeans);
        listView.setAdapter(mArrayAdapter);
        listView.setOnItemClickListener(this);
        mFrameLayout.addView(listView);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentBean item = (FragmentBean) parent.getAdapter().getItem(position);
        Fragment fragment = null;
        try {
            fragment = (Fragment) item.clazz.newInstance();
        } catch (java.lang.InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            addFragment(fragment);
        }
    }

    protected void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
