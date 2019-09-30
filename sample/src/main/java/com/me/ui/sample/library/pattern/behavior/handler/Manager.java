package com.me.ui.sample.library.pattern.behavior.handler;

import com.me.ui.util.LogUtils;

/**
 * @author studiotang on 17/5/23
 */
public class Manager extends Leader{

    @Override
    protected int limit() {
        return 5000;
    }

    @Override
    protected void handle(int money) {
        LogUtils.d(Manager.class, "经理批准" + money + "元");
    }
}
