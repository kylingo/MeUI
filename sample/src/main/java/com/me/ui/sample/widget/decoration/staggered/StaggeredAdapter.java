package com.me.ui.sample.widget.decoration.staggered;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.me.ui.library.util.MeViewUtils;
import com.me.ui.sample.base.ColorAdapter;

import java.util.Random;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    17/9/20 下午10:20
 */
public class StaggeredAdapter extends ColorAdapter {

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.tvMain.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        MeViewUtils.dip2px(holder.tvMain.getContext(), getRandom())));
    }

    protected int getRandom() {
        return new Random().nextInt(200) + 200;
    }
}