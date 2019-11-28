package com.me.ui.sample.library.music;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.me.ui.sample.R;

/**
 * @author tangqi
 * @since 2019/11/29 01:05
 */
public class MediaPlaybackService extends Service {

    public static final String PLAYSTATE_CHANGED = "com.kylingo.music.playstatechanged";
    public static final String META_CHANGED = "com.kylingo.music.metachanged";
    public static final String QUEUE_CHANGED = "com.kylingo.music.queuechanged";

    public static final String SERVICECMD = "com.kylingo.music.musicservicecommand";
    public static final String CMDNAME = "command";
    public static final String CMDTOGGLEPAUSE = "togglepause";
    public static final String CMDSTOP = "stop";
    public static final String CMDEXIT = "exit";
    public static final String CMDPAUSE = "pause";
    public static final String CMDNOISYPAUSE = "pauseByNoisy";
    public static final String CMDPLAY = "play";
    public static final String CMDPREVIOUS = "previous";
    public static final String CMDNEXT = "next";
    public static final String CMDFORWARE = "forward";
    public static final String CMDBACKWARD = "backward";
    public static final String CMDSTOPSCAN = "stopScan";

    public static final String TOGGLEPAUSE_ACTION = "com.kylingo.music.command.togglepause";
    public static final String PAUSE_ACTION = "com.kylingo.music.command.pause";
    public static final String PLAY_ACTION = "com.kylingo.music.command.play";
    public static final String PREVIOUS_ACTION = "com.kylingo.music.command.previous";
    public static final String NEXT_ACTION = "com.kylingo.music.command.next";
    public static final String REFRESH_ACTION = "com.kylingo.music.command.refresh";
    public static final String NEW_LIST_ACTION = "com.kylingo.music.command.newlist";

    private boolean isInit;
    private AudioManager mAudioManager;
    private MusicPlayer mCurrentPlayer;
    private boolean isPause;

    private NotificationManager notificationManager;
    private MediaAppWidgetProvider mAppWidgetProvider = MediaAppWidgetProvider.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        if (isInit) {
            return;
        }

        isInit = true;
        mCurrentPlayer = new MusicPlayer();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenStateChangeReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScreenStateChangeReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final Binder mBinder = new PlaybackBinder();

    public class PlaybackBinder extends Binder {
        public MediaPlaybackService getService() {
            return MediaPlaybackService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            String cmd = intent.getStringExtra(CMDNAME);

            if (CMDPLAY.equals(cmd) || PLAY_ACTION.equals(action)) {
                startPlay();
            } else if (PAUSE_ACTION.equals(action)) {
                pause();
            } else if (TOGGLEPAUSE_ACTION.equals(action)) {
                if (isPlaying()) {
                    pause();
                } else {
                    startPlay();
                }
            }
        }

        return START_NOT_STICKY;
    }

    private void startPlay() {
        int ret = mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        if (isPause || isPlaying()) {
            mCurrentPlayer.resumePlay();
            isPause = false;
        } else {
            String path = "file:///mnt/sdcard/music_test.mp3";
            mCurrentPlayer.startPlay(path);
        }

        notifyChange(MediaPlaybackService.META_CHANGED);
    }

    private void pause() {
        isPause = true;
        mCurrentPlayer.pausePlay();

        notifyChange(MediaPlaybackService.META_CHANGED);
    }

    public boolean isPlaying() {
        return mCurrentPlayer != null && mCurrentPlayer.isPlaying();
    }

    private BroadcastReceiver mScreenStateChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                mMediaplayPlayStateCheckHandler.removeCallbacksAndMessages(null);
                Message.obtain(mMediaplayPlayStateCheckHandler, 0, 1, 0).sendToTarget();
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                mMediaplayPlayStateCheckHandler.removeCallbacksAndMessages(null);
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mMediaplayPlayStateCheckHandler = new Handler() {

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);

            int next = 0;

            if (mCurrentPlayer.getPosition() < 5000) {
                next = 5500 - mCurrentPlayer.getPosition();
            }

            if ((isPlaying() || message.arg1 == 1)) {
                next = next == 0 ? 1000 : Math.min(1000, next);
            }

            updateAppWidget(MediaPlaybackService.META_CHANGED);

//            if (next > 0) {
            sendEmptyMessageDelayed(0, next);
//            }
        }
    };

    private void notifyChange(String what) {
        updateNotifition(what);
        updateAppWidget(what);
    }

    private void updateNotifition(String what) {
        //创建一个message通道，名字为消息
        createNotificationChannel("message", "消息", NotificationManager.IMPORTANCE_DEFAULT);

        final ComponentName serviceName = new ComponentName(getApplicationContext(), MediaPlaybackService.class);
        Intent intent = new Intent(MediaPlaybackService.TOGGLEPAUSE_ACTION);
        intent.setComponent(serviceName);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),
                0 /* no requestCode */, intent, 0 /* no flags */);
        /*
         * 通知布局如果使用自定义布局文件中的话要通过RemoteViews类来实现，
         * 其实无论是使用系统提供的布局还是自定义布局，都是通过RemoteViews类实现，如果使用系统提供的布局，
         * 系统会默认提供一个RemoteViews对象。如果使用自定义布局的话这个RemoteViews对象需要我们自己创建，
         * 并且加入我们需要的对应的控件事件处理，然后通过setContent(RemoteViews remoteViews)方法传参实现
         */
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.album_appwidget);
        /*
         * 对于自定义布局文件中的控件通过RemoteViews类的对象进行事件处理
         */
        remoteViews.setOnClickPendingIntent(R.id.pause, pendingIntent);
        remoteViews.setTextViewText(R.id.title, "朋友请听好");

        // Set correct drawable for pause state
        if (isPause) {
            remoteViews.setImageViewResource(R.id.pause, R.drawable.btn_play);
        } else {
            remoteViews.setImageViewResource(R.id.pause, R.drawable.btn_suspend);
        }

        Notification notification = new NotificationCompat.Builder(this, "message")
                .setContentTitle("朋友请听好") // 创建通知的标题
                .setContentText("朋友请听好") // 创建通知的内容
                .setSmallIcon(R.drawable.ic_default) // 创建通知的小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher)) // 创建通知的大图标
                /*
                 * 是使用自定义视图还是系统提供的视图，上面4的属性一定要设置，不然这个通知显示不出来
                 */
                .setDefaults(Notification.DEFAULT_ALL)  // 设置通知提醒方式为系统默认的提醒方式
                .setContent(remoteViews) // 通过设置RemoteViews对象来设置通知的布局，这里我们设置为自定义布局
                .build(); // 创建通知（每个通知必须要调用这个方法来创建）

        notificationManager.notify(1, notification); // 发送通知
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    private boolean updateAppWidget(String what) {
//        if (OSUtils.isLockWidgetVisible()) {
        mAppWidgetProvider.notifyChange(this, what);
        return true;
//        }
//        return false;
    }
}
