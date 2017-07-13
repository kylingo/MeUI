package com.me.ui.sample.widget.custom;

import android.view.View;
import android.widget.ImageView;

import com.me.ui.sample.BaseFragment;
import com.me.ui.sample.R;
import com.me.ui.widget.custom.NineGridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangqi on 17-7-13.
 */
public class NineGridFragment extends BaseFragment {

    private static final int MAX_COUNT = 9;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_nine_grid;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initView(View view) {
        NineGridLayout<Integer> nineGridLayout = (NineGridLayout) view.findViewById(R.id.nine_grid_layout);

        nineGridLayout.setImageHolder(new NineGridLayout.ImageHolder<Integer>() {
            @Override
            public void displayImage(Integer integer, ImageView imageView, int position) {
                imageView.setImageResource(integer);
            }

            @Override
            public void onItemClick(Integer integer, View view, int position) {

            }
        });
        nineGridLayout.setData(getData());
    }

    private List<Integer> getData() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < MAX_COUNT; i++) {
            list.add(R.mipmap.ic_launcher);
        }
        return list;
    }
}
