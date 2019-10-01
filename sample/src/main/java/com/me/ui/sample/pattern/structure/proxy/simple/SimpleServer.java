package com.me.ui.sample.pattern.structure.proxy.simple;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class SimpleServer implements ISimpleProxy {
    private ISimpleProxy simpleProxy;

    public SimpleServer(ISimpleProxy simpleProxy) {
        this.simpleProxy = simpleProxy;
    }

    @Override
    public void shop() {
        simpleProxy.shop();
        LogUtils.d(SimpleServer.class, "I'm server support shop service");
    }
}
