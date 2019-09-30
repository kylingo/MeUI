package com.me.ui.sample.library.pattern.create.factory;

import com.me.ui.sample.library.pattern.PatternTest;
import com.me.ui.util.LogUtils;

/**
 * @author studiotang on 17/5/10
 */
public class FactoryTest extends PatternTest {

    @Override
    public void execute() {
        BaoMaStore carStore = new BaoMaStore();
        Traffic traffic = carStore.createTraffic("baoma");
        LogUtils.d(FactoryTest.class, "traffic:" + traffic.name);
    }
}
