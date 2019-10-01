package com.me.ui.sample.pattern.behavior.visitor;

import java.util.Random;

/**
 * @author tangqi on 17-5-24.
 */
public class Engineer extends Staff {

    public Engineer(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getCodeLines() {
        return new Random().nextInt(10 * 10000);
    }
}
