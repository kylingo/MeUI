package com.me.ui.sample.library.pattern.behavior.handler;


import com.me.ui.util.LogUtils;

/**
 * @author studiotang on 17/5/23
 */
public class Boss extends Leader {

    @Override
    protected int limit() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected void handle(int money) {
        LogUtils.d(Boss.class, "老板批准" + money + "元");
    }
}
