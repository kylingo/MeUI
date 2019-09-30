package com.me.ui.sample.library.pattern.behavior.strategy;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class AircraftStrategy implements TrafficStrategy {

    @Override
    public void run() {
        LogUtils.d(AircraftStrategy.class, "坐飞机");
    }
}
