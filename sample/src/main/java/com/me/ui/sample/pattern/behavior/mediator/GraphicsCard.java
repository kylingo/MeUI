package com.me.ui.sample.pattern.behavior.mediator;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-24.
 */
public class GraphicsCard extends Colleague {

    public GraphicsCard(Mediator mediator) {
        super(mediator);
    }

    public void playVideo(String video) {
        LogUtils.d(GraphicsCard.class, "播放视频:" + video);
    }
}
