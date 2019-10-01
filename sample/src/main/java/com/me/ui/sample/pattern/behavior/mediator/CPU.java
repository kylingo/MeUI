package com.me.ui.sample.pattern.behavior.mediator;

/**
 * @author tangqi on 17-5-24.
 */
public class CPU extends Colleague {
    private String videoData;
    private String audioData;

    public CPU(Mediator mediator) {
        super(mediator);
    }

    public void decodeData(String data) {
        String[] tmp = data.split(",");
        if (tmp.length > 0) {
            videoData = tmp[0].trim();
        }

        if (tmp.length > 1) {
            audioData = tmp[1].trim();
        }

        mediator.change(this);
    }

    public String getVideoData() {
        return videoData;
    }

    public String getAudioData() {
        return audioData;
    }
}

