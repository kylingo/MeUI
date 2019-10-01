package com.me.ui.sample.pattern.structure.bridge;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class WorkStaff implements Work {

    @Override
    public void doSomeThing() {
        LogUtils.d(WorkStaff.class, "我的工作是加班，加班，加班！");
    }
}
