package com.me.ui.zhihu.page.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.ui.R;
import com.me.ui.zhihu.domain.config.Constants;
import com.me.ui.zhihu.domain.config.ExtraKey;
import com.me.ui.zhihu.domain.util.ImageLoaderUtils;
import com.me.ui.zhihu.model.entity.Daily;
import com.me.ui.zhihu.page.activity.WebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tangqi on 8/20/15.
 */
public class DailyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "DailyListAdapter";

    private List<Daily> mNewsList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private static final int ITEM_TYPE_IMAGE = 1;
    private static final int ITEM_TYPE_TEXT = 2;

    public DailyListAdapter(Context mContext, List<Daily> mNewsList) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
        mLayoutInflater = LayoutInflater.from(mContext);
        // I hate it !!!
        // http://stackoverflow.com/questions/28787008/onbindviewholder-position-is-starting-again-at-0
        // setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_IMAGE) {
            return new ImageViewHolder(mLayoutInflater.inflate(R.layout.item_daily_image_info, parent, false));
        } else {
            return new ThemeViewHolder(mLayoutInflater.inflate(R.layout.item_daily_text_info, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Daily news = mNewsList.get(position);
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_TEXT:
                ((ThemeViewHolder) holder).mTitle.setText(news.title);
                break;
            case ITEM_TYPE_IMAGE:
                ((ImageViewHolder) holder).mTitle.setText(news.title);
                ImageLoaderUtils.displayImg(news.images.get(0), ((ImageViewHolder) holder).mCover);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Daily news = mNewsList.get(position);
        return (news.images != null && news.images.size() == 0) ? ITEM_TYPE_TEXT : ITEM_TYPE_IMAGE;
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    private void gotoWebView(Daily news, View v) {
        String contentUrl = Constants.ZHIHU_DAILY_NEWS_CONTENT + news.id;
        Intent intent = new Intent(v.getContext(), WebActivity.class);
        intent.putExtra(ExtraKey.CONTENT_URL, contentUrl);
        v.getContext().startActivity(intent);
    }

    private void gotoGsonView(Daily news, View v) {
//        Log.d("Daily", "id--->" + news.id);
//        Intent intent = new Intent(v.getContext(), GsonViewActivity.class);
//        intent.putExtra(IntentKeys.EXTRA_ID, news.id);
//        intent.putExtra(IntentKeys.EXTRA_TITLE, news.title);
//        v.getContext().startActivity(intent);
    }

    private void startView(Daily news, View v) {
//        if (PrefUtils.wayToData().equals("gson")) {
//            gotoGsonView(news, v);
//        } else {
        gotoWebView(news, v);
//        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView mCover;
        @BindView(R.id.tv_title)
        TextView mTitle;

        public ImageViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick(R.id.ll_card_parent)
        void onClick(View v) {
            // TODO do what you want :) you can use WebActivity to load
            Daily news = mNewsList.get(getLayoutPosition());
            startView(news, v);
        }
    }

    public class ThemeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTitle;
        @BindView(R.id.tv_from)
        TextView mFrom;

        public ThemeViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @OnClick(R.id.ll_theme_parent)
        void onClick(View v) {
            // TODO do what you want :) you can use WebActivity to load
            Daily news = mNewsList.get(getLayoutPosition());
            startView(news, v);
        }
    }
}
