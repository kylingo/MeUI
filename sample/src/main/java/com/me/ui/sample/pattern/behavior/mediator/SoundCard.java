package com.me.ui.sample.pattern.behavior.mediator;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-24.
 */
public class SoundCard extends Colleague {

    public SoundCard(Mediator mediator) {
        super(mediator);
    }

    public void playAudio(String audio) {
        LogUtils.d(SoundCard.class, "播放音频：" + audio);
    }
}
