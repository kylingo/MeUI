package com.me.ui.sample.library.pattern.behavior.strategy;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class RailwayStrategy implements TrafficStrategy {
    @Override
    public void run() {
        LogUtils.d(RailwayStrategy.class, "坐火车");
    }
}
