package com.me.ui.sample.library.pattern.behavior.strategy;

/**
 * @author tangqi on 17-5-15.
 */
public class Travel {

    private TrafficStrategy trafficStrategy;

    public Travel() {
        trafficStrategy = new RailwayStrategy();
    }

    public void run() {
        trafficStrategy.run();
    }

    public void setTrafficStrategy(TrafficStrategy trafficStrategy) {
        this.trafficStrategy = trafficStrategy;
    }
}
