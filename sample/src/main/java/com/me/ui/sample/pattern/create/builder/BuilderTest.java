package com.me.ui.sample.pattern.create.builder;

import com.me.ui.sample.pattern.PatternTest;

/**
 * @author tangqi on 17-5-15.
 */
public class BuilderTest extends PatternTest {

    public void execute() {
        House house = new House.Builder()
                .setLayer(10)
                .setStyle(1)
                .create();
        house.show();
    }
}
