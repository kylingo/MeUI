package com.me.ui.sample.pattern.behavior.mediator;

/**
 * @author tangqi on 17-5-24.
 */
public abstract class Colleague {

    protected Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
}
