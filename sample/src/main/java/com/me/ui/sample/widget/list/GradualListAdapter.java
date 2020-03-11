package com.me.ui.sample.widget.list;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseRecyclerViewAdapter;
import com.me.ui.sample.base.BaseViewHolder;

/**
 * @author tangqi
 * @since 2020/03/10 20:25
 */
public class GradualListAdapter extends BaseRecyclerViewAdapter<String> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        ViewHolder holder = (ViewHolder) baseViewHolder;
        String title = getItem(position);
        holder.tvTitle.setText(title);
    }

    private class ViewHolder extends BaseViewHolder {

        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            tvTitle = itemView.findViewById(R.id.tvTitleList);
        }
    }
}
