package com.me.ui.sample.pattern.create.factory;

/**
 * @author studiotang on 17/5/10
 */
public class SimpleTrafficStore {

    private SimpleTrafficFactory simpleTrafficFactory;

    public SimpleTrafficStore(SimpleTrafficFactory simpleTrafficFactory) {
        this.simpleTrafficFactory = simpleTrafficFactory;
    }

    public Traffic sellTraffic(String type) {
        return simpleTrafficFactory.createTraffic(type);
    }
}
