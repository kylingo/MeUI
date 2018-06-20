package com.me.ui.sample.library;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.me.ui.library.download.DownloadItem;
import com.me.ui.library.download.DownloadStorageHelper;
import com.me.ui.library.download.DownloadTask;
import com.me.ui.library.download.DownloadTaskListener;
import com.me.ui.sample.BaseFragment;
import com.me.ui.sample.R;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 下载组件测试
 * Author:  Kevin.Tang
 * Date:    18/6/20 17:02
 */
public class DownloadFragment extends BaseFragment implements View.OnClickListener, DownloadTaskListener {

    private static final String TAG = "DownloadFragment";
    private static final String URL_TEST = "http://p0qxa46b3.bkt.clouddn.com/OfficeAlarm_v1.0.0.dmg";
    private static final int MAX_DOWNLOAD_THREAD_COUNT = 2;
    private DownloadTask mDownloadTask;
    private ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(MAX_DOWNLOAD_THREAD_COUNT,
            MAX_DOWNLOAD_THREAD_COUNT, 2, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(128));

    private TextView mTvProgress;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_download;
    }

    @Override
    protected void initView(View view) {
        initData();
        view.findViewById(R.id.btn_download_start).setOnClickListener(this);
        view.findViewById(R.id.btn_download_pause).setOnClickListener(this);
        view.findViewById(R.id.btn_download_delete).setOnClickListener(this);
        mTvProgress = view.findViewById(R.id.tv_download_progress);
    }

    private void initData() {
        DownloadStorageHelper.getInstance(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download_start:
                onDownloadStart();
                break;

            case R.id.btn_download_pause:
                onDownloadPause();
                break;

            case R.id.btn_download_delete:
                onDownloadDelete();
                break;
        }
    }

    private void onDownloadStart() {
        if (mDownloadTask == null) {
            mDownloadTask = new DownloadTask(getActivity(), createDownloadItem(),
                    DownloadStorageHelper.getDownloadFileRoot(), "test", this);
            mDownloadTask.execute(mThreadPool);
        }
    }

    private void onDownloadPause() {
        if (mDownloadTask != null) {
            LogUtils.i(TAG, "onDownloadPause state:" + mDownloadTask.getDownloadStatus());
            if (mDownloadTask.isDownloading()) {
                mDownloadTask.pause();
                ToastUtils.showShort("pause");
            } else {
                ToastUtils.showShort("state error");
            }
        } else {
            ToastUtils.showShort("task null");
        }
    }

    private void onDownloadDelete() {
        if (mDownloadTask != null) {
            LogUtils.i(TAG, "onDownloadDelete state:" + mDownloadTask.getDownloadStatus());
            mDownloadTask.remove();
            mDownloadTask = null;
            ToastUtils.showShort("remove");
        } else {
            ToastUtils.showShort("task null");
        }
    }

    private DownloadItem createDownloadItem() {
        DownloadItem downloadItem = new DownloadItem();
        downloadItem.setUri(URL_TEST);
        return downloadItem;
    }

    @Override
    public void updateProgress(DownloadTask task) {
        LogUtils.i(TAG, "updateProgress:" + task.getDownloadPercent());
        mTvProgress.setText(task.getDownloadPercent() + "%");
    }

    @Override
    public void onDownloadFinished(DownloadTask task) {
        LogUtils.i(TAG, "onDownloadFinished");
        mTvProgress.setText(R.string.download_finish);
    }

    @Override
    public void onDownloadStart(DownloadTask task) {
        LogUtils.i(TAG, "onDownloadStart");
    }

    @Override
    public void onDownloadError(DownloadTask task, int error) {
        LogUtils.i(TAG, "onDownloadError code:" + error);
        mTvProgress.setText(R.string.download_error);
    }
}
