package com.me.ui.sample.library.pattern.structure.decorator;

/**
 * @author tangqi on 17-5-12.
 */
public abstract class RoleDecorator extends Role {
    private Role role;

    public RoleDecorator(Role role) {
        this.role = role;
    }

    @Override
    void draw() {
        role.draw();
    }

    @Override
    void attack() {
        role.attack();
    }

    @Override
    void run() {
        role.run();
    }

    @Override
    void daze() {
        role.daze();
    }
}
