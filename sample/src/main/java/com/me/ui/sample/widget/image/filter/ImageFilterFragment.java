package com.me.ui.sample.widget.image.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.util.List;

/**
 * @author kylingo on 18/6/25
 * @see "https://github.com/Zomato/AndroidPhotoFilters"
 */
public class ImageFilterFragment extends BaseFragment implements ThumbnailCallback {

    private RecyclerView mRecyclerView;
    private ImageView mPlaceHolderImageView;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_image_filter;
    }

    @Override
    protected void initView(View view) {
        initUIWidgets(view);
    }

    private void initUIWidgets(View view) {
        mRecyclerView = view.findViewById(R.id.thumbnails);
        mPlaceHolderImageView = view.findViewById(R.id.place_holder_imageview);
        mPlaceHolderImageView.setImageBitmap(getOriginImageBitmap());
        initHorizontalList();
    }

    private void initHorizontalList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        bindDataToAdapter();
    }

    private void bindDataToAdapter() {
        final Context context = getContext();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                Bitmap thumbImage = getOriginImageBitmap();
                ThumbnailItem t1 = new ThumbnailItem();
                ThumbnailItem t2 = new ThumbnailItem();
                ThumbnailItem t3 = new ThumbnailItem();
                ThumbnailItem t4 = new ThumbnailItem();
                ThumbnailItem t5 = new ThumbnailItem();
                ThumbnailItem t6 = new ThumbnailItem();

                t1.image = thumbImage;
                t2.image = thumbImage;
                t3.image = thumbImage;
                t4.image = thumbImage;
                t5.image = thumbImage;
                t6.image = thumbImage;
                ThumbnailsManager.clearThumbs();
                ThumbnailsManager.addThumb(t1); // Original Image

                t2.filter = SampleFilters.getStarLitFilter();
                ThumbnailsManager.addThumb(t2);

                t3.filter = SampleFilters.getBlueMessFilter();
                ThumbnailsManager.addThumb(t3);

                t4.filter = SampleFilters.getAweStruckVibeFilter();
                ThumbnailsManager.addThumb(t4);

                t5.filter = SampleFilters.getLimeStutterFilter();
                ThumbnailsManager.addThumb(t5);

                t6.filter = SampleFilters.getNightWhisperFilter();
                ThumbnailsManager.addThumb(t6);

                List<ThumbnailItem> thumbs = ThumbnailsManager.processThumbs(context);

                ThumbnailsAdapter adapter = new ThumbnailsAdapter(thumbs,
                        ImageFilterFragment.this);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    @Override
    public void onThumbnailClick(Filter filter) {
        mPlaceHolderImageView.setImageBitmap(filter.processFilter(getOriginImageBitmap()));
    }

    private Bitmap getOriginImageBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_photo_filter);
        return Bitmap.createScaledBitmap(bitmap, 640, 640, false);
    }
}
