package com.me.ui.sample.library.pattern.behavior.command;


import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-24.
 */
public class GameMachine {

    public void toleft() {
        LogUtils.d(GameMachine.class, "向左");
    }

    public void toRight() {
        LogUtils.d(GameMachine.class, "向上");
    }

    public void toUp() {
        LogUtils.d(GameMachine.class, "向上");
    }

    public void toDown() {
        LogUtils.d(GameMachine.class, "向下");
    }
}
