package com.me.ui.sample.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ui.sample.R;

/**
 * @author kylingo on 18/4/8
 */
public class RefreshAdapter extends BaseRecyclerViewAdapter<String> {

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refresh, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
        ViewHolder holder = (ViewHolder) baseViewHolder;
        holder.tvItem.setText("item" + position);
    }

    public static class ViewHolder extends BaseViewHolder {
        TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            tvItem = itemView.findViewById(R.id.tv_refresh_item);
        }
    }
}
