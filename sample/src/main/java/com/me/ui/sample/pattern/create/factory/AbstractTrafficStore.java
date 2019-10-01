package com.me.ui.sample.pattern.create.factory;

/**
 * @author studiotang on 17/5/10
 */
public abstract class AbstractTrafficStore {

    public abstract Traffic createTraffic(String type);

    public Traffic sellTraffic(String type) {
        return createTraffic(type);
    }
}
