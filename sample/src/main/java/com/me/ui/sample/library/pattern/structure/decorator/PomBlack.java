package com.me.ui.sample.library.pattern.structure.decorator;


import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-12.
 */
public class PomBlack extends RoleDecorator {

    public PomBlack(Role role) {
        super(role);
    }

    @Override
    void draw() {
        super.draw();
        LogUtils.d(PomBlack.class, "I'm black pom");
    }
}
