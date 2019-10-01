package com.me.ui.sample.pattern.structure.decorator;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-12.
 */
public class Pom extends Role {

    @Override
    void draw() {
        LogUtils.d(Pom.class, "draw");
    }

    @Override
    void attack() {
        LogUtils.d(Pom.class, "attack");
    }

    @Override
    void run() {
        LogUtils.d(Pom.class, "run");
    }

    @Override
    void daze() {
        LogUtils.d(Pom.class, "daze");
    }
}
