package com.me.ui.sample.pattern.behavior.observer;

import com.me.ui.sample.pattern.PatternTest;

/**
 * @author tangqi on 17-5-23.
 */
public class ObserverTest extends PatternTest {

    public void execute() {
        Gank gank = new Gank();

        Coder coder1 = new Coder("coder1");
        Coder coder2 = new Coder("coder2");
        Coder coder3 = new Coder("coder3");
        gank.addObserver(coder1);
        gank.addObserver(coder2);
        gank.addObserver(coder3);

        gank.update("今日干货很棒，欢迎预览～");
    }
}
