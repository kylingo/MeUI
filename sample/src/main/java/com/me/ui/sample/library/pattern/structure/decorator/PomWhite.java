package com.me.ui.sample.library.pattern.structure.decorator;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-12.
 */
public class PomWhite extends RoleDecorator {

    public PomWhite(Role role) {
        super(role);
    }

    @Override
    void draw() {
        super.draw();
        LogUtils.d(PomWhite.class, "I'm white pom");
    }
}
