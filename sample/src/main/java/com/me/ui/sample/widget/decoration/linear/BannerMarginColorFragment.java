package com.me.ui.sample.widget.decoration.linear;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseListFragment;
import com.me.ui.sample.base.ColorAdapter;
import com.me.ui.sample.base.ColorItem;
import com.me.ui.sample.library.util.ColorUtils;
import com.me.ui.util.SizeUtils;
import com.me.ui.util.ToastUtils;
import com.me.ui.widget.decoration.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 线性带
 * @author tangqi on 17-6-20.
 */
public class BannerMarginColorFragment extends BaseListFragment {

    int TYPE_NORMAL = 0;

    int TYPE_LIST = 1;

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new LinearItemDecoration(getActivity());
    }

    @Override
    protected List<ColorItem> getData() {
        return ColorUtils.getData(3);
    }

    protected RecyclerView.Adapter getAdapter() {
        BannerAdapter adapter = new BannerAdapter();
        adapter.setItemHeight(getItemHeight());
        adapter.setData(getData());
        return adapter;
    }

    public class BannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<ColorItem> mItems;
        protected int mItemHeight;

        public BannerAdapter() {
            init();
        }

        public void setItemHeight(int itemHeight) {
            // itemHeight传0，为默认高度
            mItemHeight = itemHeight;
        }

        private void init() {
            mItems = new ArrayList<>();
        }

        public void setData(List<ColorItem> items) {
            if (items != null) {
                mItems.clear();
                mItems.addAll(items);
            }
            notifyDataSetChanged();
        }

        public void addData(List<ColorItem> items) {
            if (items != null) {
                mItems.addAll(items);
            }
        }

        private ColorItem getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_LIST) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inner_list, parent, false);
                return new InnerListHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_margin, parent, false);
                return new ColorHolder(view, mItemHeight);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ColorHolder) {
                ColorItem item = getItem(position);
                ((ColorHolder) holder).render(position, item);
            } else if (holder instanceof InnerListHolder) {
                if (position == 0) {
                    ((InnerListHolder) holder).render(SizeUtils.dp2px(200));
                } else if (position == 1) {
                    ((InnerListHolder) holder).render(SizeUtils.dp2px(60));
                }
            }

            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (position == 0) {
                layoutParams.height = SizeUtils.dp2px(200);
            } else if (position == 1){
                layoutParams.height = SizeUtils.dp2px(60);
            } else {
                layoutParams.height = SizeUtils.dp2px(200);
            }

            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                if (position == 1) {
                    holder.itemView.setAlpha(0.5f);
                    ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = -1 * layoutParams.height;
                } else {
                    ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = 0;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == 1) {
                return TYPE_LIST;
            } else {
                return TYPE_NORMAL;
            }
        }
    }

    public static class ColorHolder extends RecyclerView.ViewHolder {

        public TextView tvMain;

        public ColorHolder(View itemView, int itemHeight) {
            super(itemView);
            tvMain = itemView.findViewById(R.id.tv_grid);
            if (itemHeight > 0) {
                tvMain.getLayoutParams().height = itemHeight;
            }
        }

        public void render(int position, ColorItem item) {
            tvMain.setText(String.valueOf(item.index));
            tvMain.setBackgroundColor(item.color);
            final int currentPosition = position;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                ToastUtils.showShort("onClick position: " + currentPosition);
                }
            });

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ToastUtils.showShort("ColorHolder onTouch position: " + currentPosition);
                    return false;
                }
            });
        }
    }

    public class InnerListHolder extends RecyclerView.ViewHolder {

        public RecyclerView mRecyclerView;
        private int itemHeight = SizeUtils.dp2px(120);

        public InnerListHolder(@NonNull View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.rv_inner_list);
        }

        public void render(int itemHeight) {
            this.itemHeight = itemHeight;
            RecyclerView.LayoutManager layoutManager = getLayoutManager();
            if (layoutManager != null) {
                mRecyclerView.setLayoutManager(layoutManager);
            }

            RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
            if (itemDecoration != null) {
                mRecyclerView.addItemDecoration(itemDecoration);
            }

            initRecyclerView(mRecyclerView);
            RecyclerView.Adapter adapter = getAdapter();
            mRecyclerView.setAdapter(adapter);
        }

        protected RecyclerView.LayoutManager getLayoutManager() {
            return new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        }

        protected RecyclerView.ItemDecoration getItemDecoration() {
            return null;
        }

        protected RecyclerView.Adapter getAdapter() {
            ColorAdapter adapter = new ColorAdapter();
            adapter.setItemHeight(getItemHeight());
            adapter.setData(getData());
            return adapter;
        }

        protected int getItemHeight() {
            return itemHeight;
        }

        protected List<ColorItem> getData() {
            return ColorUtils.getData(50);
        }
    }
}
