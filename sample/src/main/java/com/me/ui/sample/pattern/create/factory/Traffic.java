package com.me.ui.sample.pattern.create.factory;

/**
 * @author studiotang on 17/5/10
 */
public class Traffic {
    protected String name;

    public Traffic(String name) {
        this.name = name;
    }

    public void create(TrafficFactory trafficFactory) {
        trafficFactory.prepare();
        trafficFactory.create();
        trafficFactory.pack();
    }
}
