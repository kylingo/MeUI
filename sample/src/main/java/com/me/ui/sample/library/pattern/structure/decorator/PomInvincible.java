package com.me.ui.sample.library.pattern.structure.decorator;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-12.
 */

public class PomInvincible extends RoleDecorator {
    public PomInvincible(Role role) {
        super(role);
    }

    @Override
    void draw() {
        super.draw();
        LogUtils.d(PomInvincible.class, "I'm pom invincible");
    }
}
