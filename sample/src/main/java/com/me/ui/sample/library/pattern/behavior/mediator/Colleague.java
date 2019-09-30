package com.me.ui.sample.library.pattern.behavior.mediator;

/**
 * @author tangqi on 17-5-24.
 */
public abstract class Colleague {

    protected Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
}
