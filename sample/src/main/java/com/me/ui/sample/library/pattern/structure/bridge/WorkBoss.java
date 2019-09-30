package com.me.ui.sample.library.pattern.structure.bridge;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class WorkBoss implements Work {

    @Override
    public void doSomeThing() {
        LogUtils.d(WorkBoss.class, "我的工作是开会，开会，开会！");
    }
}
