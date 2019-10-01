package com.me.ui.sample.pattern.behavior.observer;

import java.util.Observable;

/**
 * @author tangqi on 17-5-23.
 */
public class Gank extends Observable {

    public void update(String content) {
        setChanged();
        notifyObservers(content);
    }
}
