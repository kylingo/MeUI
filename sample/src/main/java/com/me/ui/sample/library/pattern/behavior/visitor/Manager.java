package com.me.ui.sample.library.pattern.behavior.visitor;

import java.util.Random;

/**
 * @author tangqi on 17-5-24.
 */
public class Manager extends Staff {

    private int products;

    public Manager(String name) {
        super(name);
        products = new Random().nextInt(10);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getProducts() {
        return products;
    }
}
