package com.me.ui.sample.library.pattern.behavior.handler;

/**
 * @author studiotang on 17/5/23
 */
public abstract class Leader {
    protected Leader nextHandler; //上一级

    protected abstract int limit();

    protected abstract void handle(int money);

    public void handleRequest(int money) {
        if (money <= limit()) {
            handle(money);
        } else {
            if (nextHandler != null) {
                nextHandler.handleRequest(money);
            }
        }
    }
}
