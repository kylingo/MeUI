package com.me.ui.sample.library.pattern.behavior.handler;

import com.me.ui.util.LogUtils;

/**
 * @author studiotang on 17/5/23
 */
public class GroupLeader extends Leader {

    @Override
    protected int limit() {
        return 1000;
    }

    @Override
    protected void handle(int money) {
        LogUtils.d(GroupLeader.class, "组长批准" + money + "元");
    }
}
