package com.me.ui.library.download;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import java.util.ArrayList;

public class DownloadService extends Service {

    private DownloadManager mDownloadManager;

    private volatile Looper mServiceLooper;
    private volatile ServiceHandler mServiceHandler;

    private static DownloadService mInstance;

    public static final DownloadService getInstance() {
        return mInstance;
    }

    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = (Bundle) msg.obj;

            if (mDownloadManager == null) {
                mDownloadManager = new DownloadManager(DownloadService.this);
            }

            final String id = bundle.getString(DownloadConstants.KEY_ID);
            ArrayList<String> ids = bundle.getStringArrayList(DownloadConstants.KEY_ID_LIST);

            switch (msg.what) {
            case DownloadConstants.Action.ADD:
                final DownloadItem item = (DownloadItem) bundle.getSerializable(DownloadConstants.KEY_ITEM);
                final ArrayList<DownloadItem> items = (ArrayList<DownloadItem>) bundle
                        .getSerializable(DownloadConstants.KEY_ITEM_LIST);

                if (item != null || items != null) {
                    if (item != null) {
                        mDownloadManager.addTask(item);
                    } else if (items.size() > 0) {
                        mDownloadManager.addTasks(items);
                    }
                }
                break;

            case DownloadConstants.Action.RESUME:
                if (!TextUtils.isEmpty(id)) {
                    DownloadTask task = mDownloadManager.peekTask(id);
                    if (task != null) {
                        mDownloadManager.resumeTask(id);
                    }
                }
                break;

            case DownloadConstants.Action.RESUME_ALL:
                mDownloadManager.resumeAllTask();
                break;

            case DownloadConstants.Action.REMOVE_ALL:
                mDownloadManager.removeAllTask();
                break;

            case DownloadConstants.Action.DELETE:
                boolean clearDownload = bundle.getBoolean(DownloadConstants.KEY_IS_CLEAR_DOWNLOAD, false);
                if (!TextUtils.isEmpty(id)) {
                    mDownloadManager.removeTask(id, clearDownload);
                } else if (ids != null && ids.size() > 0) {
                    mDownloadManager.removeTasks(ids, clearDownload);
                }
                break;

            case DownloadConstants.Action.PAUSE:
                if (!TextUtils.isEmpty(id)) {
                    mDownloadManager.pauseTask(id);
                }
                break;

            case DownloadConstants.Action.PAUSE_ALL:
                mDownloadManager.pauseAllTask();
                break;

            case DownloadConstants.Action.FREEZE:
                mDownloadManager.freeze();
                break;

            case DownloadConstants.Action.THAW:
                mDownloadManager.thaw();
                break;

            case DownloadConstants.Action.TASK_END:
                mDownloadManager.onTaskEnd(id);
                if (!mDownloadManager.isRunning()) {
                    // stopSelf();
                }
                break;

            default:
                break;
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        initHandler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        initHandler();

        int action = intent.getIntExtra(DownloadConstants.KEY_ACTION, -1);
        if (action >= 0) {
            Message.obtain(mServiceHandler, action, intent.getExtras()).sendToTarget();
        }

        return START_NOT_STICKY;
    }

    private void initHandler() {
        if (mServiceHandler == null) {
            HandlerThread thread = new HandlerThread("DownloadService");
            thread.start();

            mServiceLooper = thread.getLooper();
            mServiceHandler = new ServiceHandler(mServiceLooper);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mInstance = null;
        if (mDownloadManager != null) {
            mDownloadManager.stop();
        }
        mServiceLooper.quit();
        mServiceHandler = null;
    }

    @Override
    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
        onDestroy();
    }

    static void reportTaskEnd(Context context, String id) {
        context.startService(new Intent(context, DownloadService.class).putExtra(DownloadConstants.KEY_ACTION,
                DownloadConstants.Action.TASK_END).putExtra(DownloadConstants.KEY_ID, id));
    }

    public static boolean isDownloading() {
        return mInstance != null && mInstance.mDownloadManager != null && mInstance.mDownloadManager.isRunning();
    }

    public static int getDownloadStatus(String id) {
        if (isDownloading()) {
            DownloadTask task = mInstance.mDownloadManager.peekTask(id);
            if (task != null) {
                return task.getDownloadStatus();
            }
        }

        return DownloadConstants.STATUS_PAUSED;
    }
}
