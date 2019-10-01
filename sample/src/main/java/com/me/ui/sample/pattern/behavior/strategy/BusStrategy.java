package com.me.ui.sample.pattern.behavior.strategy;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class BusStrategy implements TrafficStrategy {
    @Override
    public void run() {
        LogUtils.d(BusStrategy.class, "坐大巴");
    }
}
