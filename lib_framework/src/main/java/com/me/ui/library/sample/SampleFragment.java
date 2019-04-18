package com.me.ui.library.sample;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.me.ui.quickly.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangqi on 17-6-20.
 */
public abstract class SampleFragment<T> extends AbstractSampleFragment implements
        AdapterView.OnItemClickListener {

    protected FrameLayout mFrameLayout;
    protected ArrayAdapter<T> mArrayAdapter;

    protected abstract void addItems(List<T> items);

    protected abstract void onClickItem(T item);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_sample;
    }

    @Override
    protected void initView(View view) {
        mFrameLayout = (FrameLayout) view.findViewById(R.id.fl_fragment_sample);

        ListView listView = new ListView(getActivity());
        List<T> items = new ArrayList<>();
        addItems(items);

        mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(mArrayAdapter);
        listView.setOnItemClickListener(this);
        mFrameLayout.addView(listView);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        T item = (T) parent.getAdapter().getItem(position);
        onClickItem(item);
    }
}
