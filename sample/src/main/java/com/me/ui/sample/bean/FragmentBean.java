package com.me.ui.sample.bean;

/**
 * Created by tangqi on 16-12-30.
 */

public class FragmentBean {
    public String title;
    public Class<?> clazz;

    public FragmentBean(String title, Class<?> clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return title;
    }
}

