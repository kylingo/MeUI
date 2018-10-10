package com.me.ui.library.sample;

import java.io.Serializable;

/**
 * @author tangqi on 16-12-30.
 */
public class FragmentBean implements Serializable {
    public String title;
    public Class<?> clazz;

    public FragmentBean(Class<?> clazz) {
        this(clazz.getSimpleName(), clazz);
    }

    public FragmentBean(String title, Class<?> clazz) {
        this.title = title.replace("Fragment", "");
        this.clazz = clazz;
    }


    @Override
    public String toString() {
        return title;
    }
}

