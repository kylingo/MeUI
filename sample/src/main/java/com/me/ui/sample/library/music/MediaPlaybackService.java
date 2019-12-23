package com.me.ui.sample.library.music;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
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
        mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        if (isPause || isPlaying()) {
            mCurrentPlayer.resumePlay();
            isPause = false;
        } else {
            String path = "file:///mnt/sdcard/music_test.mp3";
            mCurrentPlayer.startPlay(path);
        }

        notifyChange();
    }

    private void pause() {
        isPause = true;
        mCurrentPlayer.pausePlay();

        notifyChange();
    }

    public boolean isPlaying() {
        return mCurrentPlayer != null && mCurrentPlayer.isPlaying();
    }

    private BroadcastReceiver mScreenStateChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                updateNotification();
            }
        }
    };

    private void notifyChange() {
        updateNotification();
    }

    private void updateNotification() {
        //创建一个message通道，名字为消息
        initChannel();

        Intent intent = new Intent(getApplicationContext(), MediaPlaybackService.class);
        intent.setAction(MediaPlaybackService.TOGGLEPAUSE_ACTION);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.album_appwidget);
        remoteViews.setOnClickPendingIntent(R.id.pause, pendingIntent);
        remoteViews.setTextViewText(R.id.title, "朋友请听好");

        // Set correct drawable for pause state
        int resourceId;
        if (isPause) {
            resourceId = R.drawable.btn_play;
        } else {
            resourceId = R.drawable.btn_suspend;
        }
        remoteViews.setImageViewResource(R.id.pause, resourceId);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "message")
                .setContentTitle("音频FM")
                .setContentText("朋友请听好")
                .setSmallIcon(R.drawable.ic_default)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_sample_launcher))
                .setDefaults(Notification.DEFAULT_ALL)
                .setContent(remoteViews);

        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(1, notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void initChannel() {
        createNotificationChannel("message", "消息", NotificationManager.IMPORTANCE_DEFAULT);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationManager.createNotificationChannel(notificationChannel);
    }
}
