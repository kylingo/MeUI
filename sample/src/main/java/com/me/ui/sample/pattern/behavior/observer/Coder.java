package com.me.ui.sample.pattern.behavior.observer;

import com.me.ui.util.LogUtils;

import java.util.Observable;
import java.util.Observer;

/**
 * @author tangqi on 17-5-23.
 */
public class Coder implements Observer {

    private String name;

    public Coder(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable observable, Object data) {
        LogUtils.d(Coder.class, "Hi, " + name + ", " + data);

    }
}
