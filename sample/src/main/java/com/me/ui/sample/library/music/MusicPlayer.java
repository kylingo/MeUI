package com.me.ui.sample.library.music;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * @author tangqi
 * @since 2019/11/29 01:23
 */
public class MusicPlayer {

    private MediaPlayer mPlayer;

    public MusicPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mPlayer.start();
            }
        });
    }

    public void startPlay(String url) {
        try {
            mPlayer.setDataSource(url);
            mPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pausePlay() {
        mPlayer.pause();
    }

    public void resumePlay() {
        mPlayer.start();
    }

    public void stopPlay() {
        mPlayer.stop();
    }

    public void release() {
        mPlayer.release();
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public int getPosition() {
        return mPlayer.getCurrentPosition();
    }
}
