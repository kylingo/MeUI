package com.me.ui.library.download;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class DownloadManager implements DownloadTaskListener {

    private static final int MAX_DOWNLOAD_THREAD_COUNT = 1;

    private Context mContext;
    private DownloadStorageHelper mDownloadStorageHelper;

    private CopyOnWriteArrayList<DownloadTask> mDownloadingTasks;
    private ConcurrentLinkedQueue<DownloadTask> mPausingTasks;
    private LinkedBlockingDeque<DownloadTask> mWaitingTasks;
    private List<DownloadTask> mSuccessTasks;
    private List<DownloadTask> mFailedTasks;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(MAX_DOWNLOAD_THREAD_COUNT,
            MAX_DOWNLOAD_THREAD_COUNT, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(128));

    public DownloadManager(Context context) {
        mContext = context;
        mDownloadStorageHelper = DownloadStorageHelper.getInstance(mContext);

        mDownloadingTasks = new CopyOnWriteArrayList<>();
        mWaitingTasks = new LinkedBlockingDeque<>();
        mPausingTasks = new ConcurrentLinkedQueue<>();
        mSuccessTasks = new ArrayList<>();
        mFailedTasks = new ArrayList<>();

        mDownloadStorageHelper.init();
        restoreQueue();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_PLAY);
        intentFilter.addAction(ACTION_RESUME);
        intentFilter.addAction(ACTION_PAUSE);
        intentFilter.addAction(ACTION_MOBILE_USAGE);
        mContext.registerReceiver(mNotificationActionReceiver, intentFilter);
    }

    public void stop() {
        NotificationManager mNotificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.cancel(NOTIFICATION_PROGRESS_ID);
            mNotificationManager.cancel(NOTIFICATION_MESSAGE_ID);
        }

        try {
            mContext.unregisterReceiver(mNotificationActionReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public void addTasks(ArrayList<DownloadItem> items) {
        List<DownloadTask> tasks = new ArrayList<DownloadTask>();
        for (DownloadItem item : items) {
            String id = item.getDownloadId();
            if (peekTask(id) != null) {
                DownloadConstants.e("[MANAGER] already on queue : " + id);
                continue;
            }

            try {
                DownloadConstants.d("[MANAGER] add : " + id);
                DownloadTask task = newDownloadTask(item);
                tasks.add(task);
                mWaitingTasks.offer(task);
                notifyDownloadStatusChanged(task);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        if (tasks.size() > 0) {
            mDownloadStorageHelper.updateDownloadRecord(tasks);
            startNext();
        }
    }

    public void addTask(DownloadItem item) {
        String id = item.getDownloadId();
        if (peekTask(id) != null) {
            DownloadConstants.e("[MANAGER] already on queue : " + id);
            return;
        }

        try {
            DownloadConstants.d("[MANAGER] add : " + id);
            DownloadTask task = newDownloadTask(item);
            mWaitingTasks.offer(task);
            mDownloadStorageHelper.updateDownloadRecord(task);
            notifyDownloadStatusChanged(task);

            startNext();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void pauseTask(String id) {
        if (!TextUtils.isEmpty(id)) {
            DownloadTask task = peekTask(id);
            if (task != null) {
                DownloadConstants.d("[MANAGER] pause : " + id);
                if (task.isDownloading()) {
                    pauseDownloadingTask(task, true);
                } else if (task.isWaiting()) {
                    pauseWaitingTask(task, true);
                }
                startNext();
            }
        }
    }

    public void resumeTask(String id) {
        if (!TextUtils.isEmpty(id)) {
            DownloadTask task = peekTask(id);
            if (task != null) {
                DownloadConstants.d("[MANAGER] resume : " + id);
                resumePausedTask(task, true);
                startNext();
            }
        }
    }

    public void pauseAllTask() {
        DownloadConstants.d("[MANAGER] pause all");
        for (DownloadTask task : mDownloadingTasks) {
            pauseDownloadingTask(task, false);
        }

        for (DownloadTask task : mWaitingTasks) {
            pauseWaitingTask(task, false);
        }

        startNext();
    }

    public void resumeAllTask() {
        DownloadConstants.d("[MANAGER] resume all");
        for (DownloadTask task : mPausingTasks) {
            resumePausedTask(task, false);
        }

        startNext();
    }

    public void removeAllTask() {
        DownloadConstants.d("[MANAGER] remove all");

        for (DownloadTask task : mDownloadingTasks) {
            task.remove();
        }
        mDownloadingTasks.clear();

        for (DownloadTask task : mPausingTasks) {
            task.remove();
        }
        mPausingTasks.clear();

        for (DownloadTask task : mWaitingTasks) {
            task.remove();
        }
        mWaitingTasks.clear();

        startNext();
    }

    public void removeTasks(List<String> ids, boolean clearDownloadedFile) {
        boolean isInclude = false;
        for (String id : ids) {
            removeSingleTask(id, false, clearDownloadedFile);
            if(!isInclude&&TextUtils.equals(id,mDownloadResourceId)){
                mDownloadResourceId = null;
                mDownloadResourceName = null;
                isInclude = true;
            }
        }
        startNext();
    }

    public void removeTask(String id, boolean clearDownloadedFile) {
        removeSingleTask(id, false, clearDownloadedFile);
        if(TextUtils.equals(id,mDownloadResourceId)){
            mDownloadResourceId = null;
            mDownloadResourceName = null;
        }
        startNext();
    }

    public void onTaskEnd(String id) {
        if (!TextUtils.isEmpty(id)) {
            DownloadTask task = peekTask(id);
            if (task != null) {
                if (task.isComplete()) {
                    DownloadConstants.d("[MANAGER] complete : " + id);
                    mDownloadingTasks.remove(task);
                    if (!mSuccessTasks.contains(task)) {
                        mSuccessTasks.add(task);
                    }
                    startNext();
                    mDownloadStorageHelper.updateDownloadRecord(task);
                } else if (task.isError()) {
                    DownloadConstants.d("[MANAGER] error : " + id + ", code : " + task.getError());
                    mDownloadingTasks.remove(task);
                    mPausingTasks.add(task);
                    if (!mFailedTasks.contains(task)) {
                        mFailedTasks.add(task);
                    }
                    if (task.getError() == DownloadConstants.ERROR_NETWORK) {
                        moveWaitingQueueToFailedList();
                        pauseAllTask();

                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        boolean isMobileUsageNotAllowed = false;
                        DownloadConstants.d("[MANAGER] isMobileUsageNotAllowed : " + isMobileUsageNotAllowed);
                        if (isMobileUsageNotAllowed) {
                            updateNotification(MESSAGE_MOBILE_USAGE, null);
                        } else {
                            updateNotification(MESSAGE_COMPLETE, null);
                        }
                        return;
                    } else {
                        startNext();
                    }
                }
            }
        }
    }

    public void freeze() {
        for (DownloadTask task : mDownloadingTasks) {
            task.setFrozenState(true);
        }
        for (DownloadTask task : mWaitingTasks) {
            task.setFrozenState(true);
        }
    }

    public void thaw() {
        for (DownloadTask task : mDownloadingTasks) {
            task.setFrozenState(false);
        }
        for (DownloadTask task : mWaitingTasks) {
            task.setFrozenState(false);
        }
    }

    private void startNext() {
        takeSnap();

        while (mDownloadingTasks.size() < MAX_DOWNLOAD_THREAD_COUNT) {
            DownloadTask task = mWaitingTasks.poll();
            if (task != null) {
                DownloadConstants.d("[MANAGER] start : " + task.getId());
                mDownloadingTasks.add(task);
                task.execute(mThreadPool);

                takeSnap();
            } else {
                break;
            }
        }

        isRunning.set(mDownloadingTasks.size() > 0);

        if (!isRunning()) {
            updateNotification(MESSAGE_COMPLETE, null);
        }
    }

    private void resumePausedTask(DownloadTask task, boolean head) {
        if (task != null && task.isPaused()) {
            task.resume();
            mPausingTasks.remove(task);
            mFailedTasks.remove(task);
            if (head) {
                mWaitingTasks.addFirst(task);
                notifyDownloadStatusChanged(task);
            } else {
                mWaitingTasks.add(task);
            }
        }
    }

    private void pauseDownloadingTask(DownloadTask task, boolean notify) {
        if (task != null && task.isDownloading()) {
            task.pause();
            mDownloadingTasks.remove(task);
            mPausingTasks.add(task);
            if (notify) {
                notifyDownloadStatusChanged(task);
            }
        }
    }

    private void pauseWaitingTask(DownloadTask task, boolean notify) {
        if (task != null && task.isWaiting()) {
            task.pause();
            mWaitingTasks.remove(task);
            mPausingTasks.add(task);
            if (notify) {
                notifyDownloadStatusChanged(task);
            }
        }
    }

    private void removeSingleTask(String id, boolean notify, boolean clearDownloadedFile) {
        if (!TextUtils.isEmpty(id)) {
            DownloadTask task = peekTask(id);

            if (task != null) {
                DownloadConstants.d("[MANAGER] remove : " + id);

                if (task.isDownloading()) {
                    mDownloadingTasks.remove(task);
                } else if (task.isWaiting()) {
                    mWaitingTasks.remove(task);
                } else if (task.isPaused()) {
                    mPausingTasks.remove(task);
                }
                mFailedTasks.remove(task);
                task.remove();

                if (notify) {
                    notifyDownloadStatusChanged(task);
                }
            }
        }
    }

    public DownloadTask peekTask(String id) {
        for (DownloadTask task : mDownloadingTasks) {
            if (task != null && task.isEquals(id)) {
                return task;
            }
        }

        for (DownloadTask task : mPausingTasks) {
            if (task != null && task.isEquals(id)) {
                return task;
            }
        }

        for (DownloadTask task : mWaitingTasks) {
            if (task != null && task.isEquals(id)) {
                return task;
            }
        }

        return null;
    }

    private DownloadTask newDownloadTask(DownloadItem item) throws MalformedURLException {
        return new DownloadTask(mContext, item, DownloadStorageHelper.getDownloadFileRoot(), item.getName(), this);
    }

    @Override
    public void updateProgress(DownloadTask task) {
        notifyDownloadProgressChanged(task);
    }

    @Override
    public void onDownloadStart(DownloadTask task) {
        notifyDownloadStatusChanged(task);
    }

    @Override
    public void onDownloadFinished(DownloadTask task) {
        DownloadService.reportTaskEnd(mContext, task.getId());
        notifyDownloadStatusChanged(task);
    }

    @Override
    public void onDownloadError(DownloadTask task, int error) {
        DownloadService.reportTaskEnd(mContext, task.getId());
        notifyDownloadStatusChanged(task);
    }

    private void notifyDownloadStatusChanged(DownloadTask task) {
        Intent notifyIntent = new Intent(DownloadConstants.ACTION_DOWNLOAD_STATUS);
        notifyIntent.putExtra(DownloadConstants.KEY_STATUS, task.getDownloadStatus());
        notifyIntent.putExtra(DownloadConstants.KEY_ERROR_CODE, task.getError());
        notifyIntent.putExtra(DownloadConstants.KEY_TOTAL_SIZE, task.getTotalSize());
        notifyIntent.putExtra(DownloadConstants.KEY_ID, task.getId());
        mContext.sendBroadcast(notifyIntent);
        updateNotification(MESSAGE_PROGRESS_UPDATE, task);
    }

    private void notifyDownloadProgressChanged(DownloadTask task) {
        Intent updateIntent = new Intent(DownloadConstants.ACTION_DOWNLOAD_STATUS);
        updateIntent.putExtra(DownloadConstants.KEY_DOWNLOADED_SIZE, task.getDownloadSize());
        updateIntent.putExtra(DownloadConstants.KEY_TOTAL_SIZE, task.getTotalSize());
        updateIntent.putExtra(DownloadConstants.KEY_ID, task.getId());
        mContext.sendBroadcast(updateIntent);
        updateNotification(MESSAGE_PROGRESS_UPDATE, task);
    }

    private static final int NOTIFICATION_PROGRESS_ID = 99;
    private static final int NOTIFICATION_MESSAGE_ID = 98;
    private static final int MESSAGE_COMPLETE = 1;
    private static final int MESSAGE_MOBILE_USAGE = 2;
    private static final int MESSAGE_PROGRESS_UPDATE = 3;
    private static final int MESSAGE_USER_PAUSED = 4;
    private static final String ACTION_PAUSE = "com.letv.music.notification.acton.pause";
    private static final String ACTION_PLAY = "com.letv.music.notification.acton.play";
    private static final String ACTION_RESUME = "com.letv.music.notification.acton.resume";
    private static final String ACTION_MOBILE_USAGE = "com.letv.music.notification.acton.mobile_usage";
    private String mDownloadResourceName;
    private String mFailedResourceName;
    private boolean mPlayMessageAppeared;
    private String mDownloadResourceId;

    private BroadcastReceiver mNotificationActionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager mNotificationManager = (NotificationManager) mContext
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            if (ACTION_PAUSE.equals(intent.getAction())) {
                moveWaitingQueueToFailedList();
                pauseAllTask();
                updateNotification(MESSAGE_USER_PAUSED, null);
            } else if (ACTION_RESUME.equals(intent.getAction())) {
                resumeFailedTasks();
            } else if (ACTION_MOBILE_USAGE.equals(intent.getAction())) {
                resumeFailedTasks();
            } else if (ACTION_PLAY.equals(intent.getAction())) {
                mNotificationManager.cancel(NOTIFICATION_MESSAGE_ID);
                mPlayMessageAppeared = false;
            }
        }
    };

    private void moveWaitingQueueToFailedList() {
        for (DownloadTask task : mDownloadingTasks) {
            if (!mFailedTasks.contains(task)) {
                mFailedTasks.add(task);
            }
        }
        for (DownloadTask task : mWaitingTasks) {
            if (!mFailedTasks.contains(task)) {
                mFailedTasks.add(task);
            }
        }
    }

    private void resumeFailedTasks() {
        List<DownloadTask> tasks = new ArrayList<DownloadTask>(mFailedTasks);
        for (DownloadTask task : tasks) {
            resumePausedTask(task, false);
        }

        startNext();
    }

    private void updateNotification(int msg, DownloadTask task) {
        int taskCount = mDownloadingTasks.size() + mWaitingTasks.size() + mSuccessTasks.size() + mFailedTasks.size();

        int progress = -1;
        if (task != null && task.getTotalSize() > 0) {
            progress = (int) (task.getDownloadSize() * 100 / task.getTotalSize());
        }
        if (msg == MESSAGE_PROGRESS_UPDATE && taskCount > 0) {
            progress = ((mSuccessTasks.size() + mFailedTasks.size()) * 100 + progress) / taskCount;
        }

        if (TextUtils.isEmpty(mDownloadResourceName) && task != null) {
            mDownloadResourceName = task.getDownloadFile().getName();
            mDownloadResourceId = task.getId();
        }
        if (mDownloadResourceName == null) {
            mDownloadResourceName = "";
        }
        String shortcutResourceName = mDownloadResourceName.length() > 15 ? mDownloadResourceName.substring(0, 15)
                : mDownloadResourceName;
        if (mFailedResourceName == null) {
            mFailedResourceName = "";
        }
        String shortcutFailedResourceName = mFailedResourceName.length() > 15 ? mFailedResourceName.substring(0, 15)
                : mFailedResourceName;

        String title = null, content = null;
        Intent action1 = null;
        String actionString1 = null;
        int actionIcon1 = 0;
        boolean playMessageAppeared = false;

//        switch (msg) {
//        case MESSAGE_PROGRESS_UPDATE:
//            title = mContext.getString(R.string.notification_music_download_title);
//            content = mDownloadResourceName;
//            if (taskCount > 1 && !TextUtils.isEmpty(content)) {
//                content = mContext.getString(R.string.notification_music_download_content, content, taskCount);
//                action1 = new Intent(ACTION_PAUSE);
//                actionString1 = mContext.getString(R.string.notification_music_download_action_pause);
//                actionIcon1 = R.drawable.ic_notification_action_pause;
//            }
//            break;
//        case MESSAGE_MOBILE_USAGE:
//        case MESSAGE_USER_PAUSED:
//            if (msg == MESSAGE_MOBILE_USAGE) {
//                title = mContext.getString(R.string.notification_music_download_mobile_usage_title);
//
//                content = mDownloadResourceName;
//                if (taskCount > 1 && !TextUtils.isEmpty(content)) {
//                    content = mContext.getString(R.string.notification_music_download_content, content, taskCount);
//                }
//            } else {
//                if (taskCount > 1 && !TextUtils.isEmpty(mDownloadResourceName)) {
//                    title = mContext.getString(R.string.notification_music_download_user_paused_multi_title,
//                            mDownloadResourceName, taskCount);
//                } else {
//                    title = mContext.getString(R.string.notification_music_download_user_paused_single_title,
//                            mDownloadResourceName);
//                }
//                content = mContext.getString(R.string.notification_music_download_user_paused_content,
//                        mSuccessTasks.size(), mFailedTasks.size());
//            }
//            if (msg == MESSAGE_MOBILE_USAGE) {
//                action1 = new Intent(ACTION_MOBILE_USAGE);
//            } else {
//                action1 = new Intent(ACTION_RESUME);
//            }
//            actionString1 = mContext.getString(R.string.notification_music_download_action_resume);
//            actionIcon1 = R.drawable.ic_notification_action_resume;
//            break;
//        case MESSAGE_COMPLETE:
//            if (mFailedTasks.size() < 1) {
//                if (taskCount < 2) {
//                    title = mContext.getString(R.string.notification_music_download_finish_single_title,
//                            mDownloadResourceName);
//                } else if (taskCount > 0) {
//                    title = mContext.getString(R.string.notification_music_download_finish_multi_title,
//                            shortcutResourceName, taskCount);
//                }
//                if (mSuccessTasks.size() > 0) {
//                    content = mContext.getString(R.string.notification_music_download_finish_content,
//                            mSuccessTasks.size());
//                    mSuccessTasks.clear();
//                }
//                mDownloadResourceName = null;
//                mDownloadResourceId = null;
//                mFailedResourceName = null;
//                if (mPlayableTracks.size() > 0) {
//                    action1 = new Intent(ACTION_PLAY);
//                    actionString1 = mContext.getString(R.string.notification_music_download_action_play);
//                    actionIcon1 = R.drawable.ic_notification_action_play;
//                    playMessageAppeared = true;
//                }
//            } else {
//                if (taskCount < 2) {
//                    title = mContext.getString(R.string.notification_music_download_finish_fail_single_title,
//                            mFailedResourceName);
//                } else {
//                    title = mContext.getString(R.string.notification_music_download_finish_fail_multi_title,
//                            shortcutFailedResourceName, taskCount);
//                }
//                content = mContext.getString(R.string.notification_music_download_finish_fail_content,
//                        mSuccessTasks.size(), mFailedTasks.size());
//                action1 = new Intent(ACTION_RESUME);
//                actionString1 = mContext.getString(R.string.notification_music_download_action_resume);
//                actionIcon1 = R.drawable.ic_notification_action_resume;
//            }
//            break;
//        }
//
//        NotificationCompat.Builder mBuilder = null;
//        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
//            mBuilder = new NotificationCompat.Builder(mContext).setSmallIcon(R.drawable.app_music).setAutoCancel(false)
//                    .setContentTitle(title).setContentText(content);
//        }
//
//        if (mBuilder != null && progress >= 0) {
//            mBuilder.setProgress(100, progress, false);
//            mBuilder.setOngoing(true);
//        }
//
//        if (mBuilder != null && action1 != null) {
//            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(mContext, 0, action1,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            mBuilder.addAction(actionIcon1, actionString1, pendingIntent1);
//        }
//
//        NotificationManager mNotificationManager = (NotificationManager) mContext
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        int notifyId = msg == MESSAGE_PROGRESS_UPDATE ? NOTIFICATION_PROGRESS_ID : NOTIFICATION_MESSAGE_ID;
//        if (notifyId == NOTIFICATION_MESSAGE_ID) {
//            mNotificationManager.cancel(NOTIFICATION_PROGRESS_ID);
//            mPlayMessageAppeared = playMessageAppeared;
//        } else if (notifyId == NOTIFICATION_PROGRESS_ID && !mPlayMessageAppeared) {
//            mNotificationManager.cancel(NOTIFICATION_MESSAGE_ID);
//        }
//
//        // launch download manager activity
//        if (mBuilder != null) {
//            Intent downloadBrowserIntent = new Intent(MusicExternalRequestIntentUtils
//                    .ACTION_VIEW_DOWNLOAD_BROWSER);
//            downloadBrowserIntent.putExtra("tab", isPureVideoTask ? 1 : 0);
//            mBuilder.setContentIntent(PendingIntent.getBroadcast(mContext, 0, downloadBrowserIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT));
//        }
//
//        if (mBuilder != null) {
//            Notification notification = mBuilder.build();
//            notification.notificationIcon = R.drawable.ic_status_bar;
//            if (notifyId == NOTIFICATION_MESSAGE_ID) {
//                notification.flags |= Notification.FLAG_AUTO_CANCEL;
//            }
//            mNotificationManager.notify(notifyId, notification);
//        }
    }

    private void restoreQueue() {
        List<DownloadItem> recordList = mDownloadStorageHelper.getDownloadRecords(-1, -1, null);
        if (recordList != null && recordList.size() > 0) {
            for (DownloadItem record : recordList) {
                if (record.getStatus() != DownloadConstants.STATUS_COMPLETE) {
                    DownloadTask newTask = new DownloadTask(mContext, record, this);
                    if (DownloadConstants.AUTO_START_ON_LOGIN) {
                        mWaitingTasks.add(newTask);
                    } else {
                        newTask.pause();
                        mPausingTasks.add(newTask);
                    }
                }
            }
        }

        DownloadConstants.d("[MANAGER] reload queue.");
        takeSnap();

        if (DownloadConstants.AUTO_START_ON_LOGIN) {
            startNext();
        }
    }

    private void takeSnap() {
        DownloadConstants.d("[MANAGER] snap ### downloading : " + mDownloadingTasks.size() + ", waiting : "
                + mWaitingTasks.size() + ", pausing : " + mPausingTasks.size());
    }
}