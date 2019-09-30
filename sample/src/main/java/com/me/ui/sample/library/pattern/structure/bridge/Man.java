package com.me.ui.sample.library.pattern.structure.bridge;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class Man extends Person {
    public Man(Work work) {
        super(work);
    }

    @Override
    public void createPerson() {
        LogUtils.d(Man.class, "我是一个男人");
    }
}
