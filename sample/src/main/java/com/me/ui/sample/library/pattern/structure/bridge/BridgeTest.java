package com.me.ui.sample.library.pattern.structure.bridge;

import com.me.ui.sample.library.pattern.PatternTest;

/**
 * @author tangqi on 17-5-15.
 */
public class BridgeTest extends PatternTest {

    public void execute() {
        WorkBoss workBoss = new WorkBoss();
        WorkStaff workStaff = new WorkStaff();

        Man manBoss = new Man(workBoss);
        manBoss.work();

        Man manStaff = new Man(workStaff);
        manStaff.work();

        Woman womanBoss = new Woman(workBoss);
        womanBoss.work();

        Woman womanStaff = new Woman(workStaff);
        womanStaff.work();
    }
}
