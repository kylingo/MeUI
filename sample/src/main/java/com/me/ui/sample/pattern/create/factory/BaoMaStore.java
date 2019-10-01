package com.me.ui.sample.pattern.create.factory;

/**
 * @author studiotang on 17/5/10
 */
class BaoMaStore extends AbstractTrafficStore {

    @Override
    public Traffic createTraffic(String type) {
        Traffic traffic = new Traffic(type);
        if (type.equals("baoma")) {
            traffic.create(new BaoMaFactory());
        }
        return traffic;
    }
}
