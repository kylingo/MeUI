package com.me.ui.sample.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author kylingo on 18/4/8
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected abstract void initView(View itemView);

    public BaseViewHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }
}
