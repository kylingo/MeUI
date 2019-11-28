package com.me.ui.sample.library.music;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

/**
 * @author tangqi
 * @since 2019/11/29 01:10
 */
public class PlaybackUtils {

    public static void startService(Context context) {
        Intent intent = new Intent(context, MediaPlaybackService.class);
        context.startService(intent);
    }

    public static void startPlay(final Context context) {
        Intent intent = new Intent(context, MediaPlaybackService.class);
        intent.setAction(MediaPlaybackService.SERVICECMD);
        intent.putExtra(MediaPlaybackService.CMDNAME, MediaPlaybackService.CMDPLAY);
        context.startService(intent);
    }

    public static void bindToService(Activity context, ServiceConnection callback) {
        Intent intent = new Intent(context, MediaPlaybackService.class);
        context.startService(intent);
        if (callback != null) {
            context.bindService(intent, callback, 0);
        }
    }

    public static void unbindFromService(Activity context, ServiceConnection callback) {
        try {
            context.unbindService(callback);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void togglePlaying(Context context) {
        Intent i = new Intent(context, MediaPlaybackService.class);
        i.setAction(MediaPlaybackService.TOGGLEPAUSE_ACTION);
        context.startService(i);
    }

    public static void next(Context context) {
        Intent i = new Intent(context, MediaPlaybackService.class);
        i.setAction(MediaPlaybackService.NEXT_ACTION);
        context.startService(i);
    }

    public static void previous(Context context) {
        Intent i = new Intent(context, MediaPlaybackService.class);
        i.setAction(MediaPlaybackService.PREVIOUS_ACTION);
        context.startService(i);
    }

    public static void pause(Context context) {
        Intent i = new Intent(context, MediaPlaybackService.class);
        i.setAction(MediaPlaybackService.PAUSE_ACTION);
        context.startService(i);
    }

    public static void play(Context context) {
        Intent i = new Intent(context, MediaPlaybackService.class);
        i.setAction(MediaPlaybackService.PLAY_ACTION);
        context.startService(i);
    }

    public static void exit(Context context) {
        Intent i = new Intent(context, MediaPlaybackService.class);
        i.putExtra(MediaPlaybackService.CMDNAME, MediaPlaybackService.CMDEXIT);
        context.startService(i);
    }
}
