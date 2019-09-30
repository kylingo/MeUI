package com.me.ui.sample.library.pattern.structure.bridge;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class Woman extends Person {
    public Woman(Work work) {
        super(work);
    }

    @Override
    public void createPerson() {
        LogUtils.d(Woman.class, "我是一个女人");
    }
}
