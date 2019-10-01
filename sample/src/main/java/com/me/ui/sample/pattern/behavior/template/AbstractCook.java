package com.me.ui.sample.pattern.behavior.template;

/**
 * @author tangqi on 17-5-18.
 */
public abstract class AbstractCook {

    protected abstract void buyFood();

    protected abstract void prepareCook();

    protected abstract void cook();
    
    public final void execute() {
        buyFood();

        prepareCook();

        cook();
    }
}
