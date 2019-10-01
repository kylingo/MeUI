package com.me.ui.sample.pattern.behavior.visitor;

import java.util.Random;

/**
 * @author tangqi on 17-5-24.
 */
public abstract class Staff {
    public String name;
    public int kpi;

    public Staff(String name) {
        this.name = name;
        kpi = new Random().nextInt(100);
    }

    public abstract void accept(Visitor visitor);
}
