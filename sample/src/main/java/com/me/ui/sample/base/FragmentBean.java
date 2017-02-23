package com.me.ui.sample.base;

/**
 * @author tangqi on 16-12-30.
 */
public class FragmentBean {
    public String title;
    Class<?> clazz;

    public FragmentBean(String title, Class<?> clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return title;
    }
}

