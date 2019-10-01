package com.me.ui.sample.pattern.create.factory;

/**
 * @author studiotang on 17/5/10
 */
public class SimpleTrafficFactory {

    public Traffic createTraffic(String type) {
        if ("car".equals(type)) {
            return new Traffic("car");
        } else if ("bus".equals(type)) {
            return new Traffic("bug");
        } else if ("plane".equals(type)) {
            return new Traffic("plane");
        }

        return null;
    }
}
