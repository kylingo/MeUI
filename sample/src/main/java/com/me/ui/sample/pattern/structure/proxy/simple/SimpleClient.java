package com.me.ui.sample.pattern.structure.proxy.simple;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class SimpleClient implements ISimpleProxy {
    @Override
    public void shop() {
        LogUtils.d(SimpleClient.class, "I'm client want to shop");
    }
}
